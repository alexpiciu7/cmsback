package com.project.cms.controller;

import com.project.cms.model.*;
import com.project.cms.payload.request.CourseRegister;
import com.project.cms.repository.PendingCourseEnrollmentRepository;
import com.project.cms.service.*;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/instructor")
public class InstructorController {
    private final CourseService courseService;
    private final InstructorService instructorService;
    private final StudentService studentService;
    private final NoteService noteService;
    private final Mapper mapper;
    private final PendingCourseEnrollmentRepository pendingCourseEnrollmentRepository;

    @Autowired
    public InstructorController(CourseService courseService, InstructorService instructorService, StudentService studentService, NoteService noteService, Mapper mapper, PendingCourseEnrollmentRepository pendingCourseEnrollmentRepository) {
        this.courseService = courseService;
        this.instructorService = instructorService;
        this.studentService = studentService;
        this.noteService = noteService;
        this.mapper = mapper;
        this.pendingCourseEnrollmentRepository = pendingCourseEnrollmentRepository;
    }

    @PostMapping("{email}/add/course")
//    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<?> addCourse(@PathVariable String email, @ModelAttribute CourseRegister courseRegister) {
        try {
            Optional<Instructor> instructor = instructorService.findOne(email);
            if (instructor.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Course course = new Course();
            course.updateFields(courseRegister);
            if (courseRegister.getImage() != null) {
                course.setImageURL(Base64.getEncoder().encodeToString(courseRegister.getImage().getBytes()));
            } else
                course.setImageURL("");
            courseService.save(course);
            instructor.get().addCourse(course);
            instructorService.save(instructor.get());
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception E) {
            E.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PutMapping("/deactivate/course/{id}")
    public ResponseEntity<?> deactivateCourse(@PathVariable String id) {
        Optional<Course> course = courseService.findOne(id);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();
        course.get().setActive(false);
        return ResponseEntity.ok(courseService.save(course.get()));
    }

    @PutMapping("/activate/course/{id}")
    public ResponseEntity<?> activateCourse(@PathVariable String id) {
        Optional<Course> course = courseService.findOne(id);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();
        course.get().setActive(true);
        return ResponseEntity.ok(courseService.save(course.get()));
    }

    @PutMapping("/course/{id}/capacity")
    public ResponseEntity<?> capacity(@PathVariable String id, @RequestBody @Positive int capacity) {
        Optional<Course> course = courseService.findOne(id);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();
        course.get().setCapacity(capacity);
        return ResponseEntity.ok(courseService.save(course.get()));
    }

    @PutMapping("/course/{id}/timetable")
    public ResponseEntity<?> timetable(@PathVariable String id, @RequestBody MultipartFile timetable) throws IOException {
        Optional<Course> course = courseService.findOne(id);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();
        course.get().setTimetable(Base64.getEncoder().encodeToString(timetable.getBytes()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/course/{id}/update")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody CourseRegister courseRegister) {
        Optional<Course> course = courseService.findOne(id);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();
        course.get().updateFields(courseRegister);
        return ResponseEntity.ok(courseService.save(course.get()));
    }

    @GetMapping("/student/{email}/cv")
    public ResponseEntity<?> cvStudent(@PathVariable String email) {
        Optional<Student> student = studentService.findOne(email);
        if (student.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(student.get().getCv());
    }

    @PutMapping("/course/{id}/post")
    public ResponseEntity<?> post(@PathVariable String id, @RequestBody String post) {
        Optional<Course> course = courseService.findOne(id);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();
        course.get().addPost(post);
        return ResponseEntity.ok(courseService.save(course.get()));
    }

    @PostMapping("/course/{courseId}/student/{email}/note")
    public ResponseEntity<?> note(@PathVariable String courseId, @PathVariable String email, @RequestBody String note) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AnonymousAuthenticationToken)
            return ResponseEntity.badRequest().body("You must be logged!");
        Optional<Course> course = courseService.findOne(courseId);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();
        Optional<Student> student = studentService.findOne(email);
        if (student.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(noteService.save(new Note(((UserDetails) principal).getUsername(), student.get().getEmail(), courseId, note)));

    }

    @PutMapping("/course/{courseId}/enroll/{email}")
    public ResponseEntity<?> addStudentAtCourse(@PathVariable String courseId, @PathVariable String email) {
        Optional<Course> course = courseService.findOne(courseId);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();
        Optional<Student> student = studentService.findOne(email);
        if (student.isEmpty())
            return ResponseEntity.notFound().build();
        Optional<PendingCourseEnrollment> pendingCourseEnrollment = pendingCourseEnrollmentRepository.findByCourseIdAndStudentEmail(courseId, email);
        if (pendingCourseEnrollment.isEmpty())
            return ResponseEntity.notFound().build();
        if (course.get().getCapacity() - course.get().getStudents().size() - 1 > 0) {
            course.get().addStudent(student.get());
            pendingCourseEnrollmentRepository.delete(pendingCourseEnrollment.get());
            return ResponseEntity.ok(courseService.save(course.get()));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("{email}/course/enrolment")
    public ResponseEntity<?> getPendingEnrollment(@PathVariable String email) {
        Optional<Instructor> instructor = instructorService.findOne(email);
        if (instructor.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<String> courseIds = instructor.get().getCourses().stream().map(Course::getId).collect(Collectors.toList());
        return ResponseEntity.ok(pendingCourseEnrollmentRepository.findAllWithIdIn(courseIds));
    }

}


