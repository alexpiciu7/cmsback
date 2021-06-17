package com.project.cms.controller;

import com.project.cms.model.*;
import com.project.cms.payload.request.CourseRegister;
import com.project.cms.payload.request.GroupRequest;
import com.project.cms.repository.PendingCourseEnrollmentRepository;
import com.project.cms.repository.PendingGroupEnrollmentRepository;
import com.project.cms.service.*;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.util.Optional;

@RestController
@RequestMapping("/instructor")
public class InstructorController {
    private final CourseService courseService;
    private final InstructorService instructorService;
    private final StudentService studentService;
    private final NoteService noteService;
    private final FilesStorageService filesStorageService;
    private final Mapper mapper;
    private final PendingCourseEnrollmentRepository pendingCourseEnrollmentRepository;
    private final PendingGroupEnrollmentRepository pendingGroupEnrollmentRepository;

    @Autowired
    public InstructorController(CourseService courseService, InstructorService instructorService, StudentService studentService, NoteService noteService, FilesStorageService filesStorageService, Mapper mapper, PendingCourseEnrollmentRepository pendingCourseEnrollmentRepository, PendingGroupEnrollmentRepository pendingGroupEnrollmentRepository) {
        this.courseService = courseService;
        this.instructorService = instructorService;
        this.studentService = studentService;
        this.noteService = noteService;
        this.filesStorageService = filesStorageService;
        this.mapper = mapper;
        this.pendingCourseEnrollmentRepository = pendingCourseEnrollmentRepository;
        this.pendingGroupEnrollmentRepository = pendingGroupEnrollmentRepository;
    }

    @PostMapping("/add/course")
//    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<?> addCourse(@RequestBody CourseRegister courseRegister) {
        Course course = mapper.map(courseRegister, Course.class);
        try {
            courseService.save(course);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception E) {
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
    public ResponseEntity<?> timetable(@PathVariable String id, @RequestBody MultipartFile timetable) {
        Optional<Course> course = courseService.findOne(id);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();
        filesStorageService.saveTimetable(timetable, course.get().getTimetable());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/course/{id}/update")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody CourseRegister courseRegister) {
        Optional<Course> course = courseService.findOne(id);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();
        course.get().setCourse(courseRegister);
        return ResponseEntity.ok(courseService.save(course.get()));
    }

    @GetMapping("/student/{email}/cv")
    public ResponseEntity<?> cvStudent(@PathVariable String email) {
        Optional<Student> student = studentService.findOne(email);
        if (student.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(filesStorageService.loadCv(student.get().getCv()));
    }

    @PutMapping("/course/{id}/post")
    public ResponseEntity<?> post(@PathVariable String id, @RequestBody String post) {
        Optional<Course> course = courseService.findOne(id);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();
        course.get().addPost(post);
        return ResponseEntity.ok(courseService.save(course.get()));
    }

    @PostMapping("/course/{idCourse}/student/{email}/note")
    public ResponseEntity<?> note(@PathVariable String idCourse, @PathVariable String email, @RequestBody String note) {

        Optional<Course> course = courseService.findOne(idCourse);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();
        Optional<Student> student = studentService.findOne(email);
        if (student.isEmpty())
            return ResponseEntity.notFound().build();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof AnonymousAuthenticationToken)) {
            return ResponseEntity.ok(noteService.save(new Note(((UserDetails) principal).getUsername(), student.get().getEmail(), idCourse, note)));
        } else return ResponseEntity.badRequest().body("You must be logged!");

    }

    @PutMapping("/course/{id}/add/group")
    public ResponseEntity<?> addGroup(@PathVariable String id, @RequestBody GroupRequest group) {
        Optional<Course> course = courseService.findOne(id);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();

        Group group1 = mapper.map(group, Group.class);
        group1.setCourseId(id);
        courseService.saveGroup(group1);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/course/{idCourse}/enroll/{email}")

    public ResponseEntity<?> addStudentAtCourse(@PathVariable String idCourse, @PathVariable String email) {
        Optional<Course> course = courseService.findOne(idCourse);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();
        Optional<Student> student = studentService.findOne(email);
        if (student.isEmpty())
            return ResponseEntity.notFound().build();
        Optional<PendingCourseEnrollment> pendingCourseEnrollment = pendingCourseEnrollmentRepository.findByCourseIdAndStudentEmail(idCourse, email);
        if (pendingCourseEnrollment.isEmpty())
            return ResponseEntity.notFound().build();
        if (course.get().getCapacity() - course.get().getStudents().size() - 1 > 0) {
            course.get().addStudent(student.get());
            pendingCourseEnrollmentRepository.delete(pendingCourseEnrollment.get());
            return ResponseEntity.ok(courseService.save(course.get()));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/course/{idCourse}/group/{groupNo}/student/{email}")
    public ResponseEntity<?> addStudentInGroup(@PathVariable String idCourse, @PathVariable String groupNo,
                                               @PathVariable String email) {
        Optional<Course> course = courseService.findOne(idCourse);
        Optional<Student> student = studentService.findOne(email);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();
        if (student.isEmpty())
            return ResponseEntity.notFound().build();
        Optional<PendingGroupEnrollment> group = pendingGroupEnrollmentRepository.findByCourseIdAndGroupNoAndStudentEmail(idCourse, groupNo, email);
        if (group.isEmpty())
            return ResponseEntity.notFound().build();
        if (!course.get().getStudents().contains(student.get()))
            return ResponseEntity.badRequest().build();
        Optional<Group> group1 = courseService.findByCourseIdAndGroupNo(idCourse, groupNo);
        if (group1.isEmpty())
            return ResponseEntity.notFound().build();
        if (group1.get().getCapacity() - group1.get().getStudents().size() - 1 > 0) {
            group1.get().addStudent(student.get());
            pendingGroupEnrollmentRepository.delete(group.get());
            return ResponseEntity.ok(courseService.saveGroup(group1.get()));
        }
        return ResponseEntity.badRequest().build();
    }


}


