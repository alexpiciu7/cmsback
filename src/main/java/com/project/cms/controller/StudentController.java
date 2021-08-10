package com.project.cms.controller;

import com.project.cms.model.Course;
import com.project.cms.model.PendingCourseEnrollment;
import com.project.cms.model.Student;
import com.project.cms.payload.request.StudentRegister;
import com.project.cms.payload.response.CourseDetailResponse;
import com.project.cms.payload.response.CourseResponse;
import com.project.cms.payload.response.StudentResponse;
import com.project.cms.service.ICourseService;
import com.project.cms.service.IFilesStorageService;
import com.project.cms.service.IStudentService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/student")
public class StudentController {

    private final IStudentService studentService;
    private final ICourseService courseService;
    private final IFilesStorageService filesStorageService;
    private final Mapper mapper;

    @Autowired
    public StudentController(IStudentService studentService, ICourseService courseService, IFilesStorageService filesStorageService, Mapper mapper) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.filesStorageService = filesStorageService;
        this.mapper = mapper;
    }

    @Transactional
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {"multipart/form-data"}, produces =
            "application/json")
    public ResponseEntity<?> registerUser(@ModelAttribute StudentRegister studentRegister) {

        try {
            return ResponseEntity.ok(studentService.register(studentRegister, studentRegister.getCv()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getCause());
        }
    }

    @GetMapping("/course/all")
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok(courseService.getAll().stream().map(x -> mapper.map(x, CourseResponse.class)).peek(
                x -> x.setImageURL(Path.of("course").toAbsolutePath() + x.getImageURL())
        ));
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<?> getDetailsCourse(@PathVariable String id) {
        Optional<Course> course = courseService.findOne(id);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();
        CourseDetailResponse courseResponse = mapper.map(course.get(), CourseDetailResponse.class);
        courseResponse.setImageURL(Path.of("course").toAbsolutePath() + course.get().getImageURL());
        courseResponse.setTimetable(course.get().getTimetable());
        return ResponseEntity.ok(courseResponse);
    }

    @PostMapping("/{email}/course/{id}/enroll")
    public ResponseEntity<?> enrollCourse(@PathVariable String email, @PathVariable String id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AnonymousAuthenticationToken)
            return ResponseEntity.badRequest().body("You must be logged!");
        Optional<Course> course = courseService.findOne(id);
        Optional<Student> student = studentService.findOne(email);
        if (course.isEmpty() || student.isEmpty())
            return ResponseEntity.notFound().build();
        if (!course.get().isActive())
            return ResponseEntity.badRequest().body("Course is not active!");
        if (!(course.get().getRegisterDuration().getStartDate().after(new Date()) && course.get().getRegisterDuration().getEndDate().before(new Date())))
            return ResponseEntity.badRequest().body("Wrong date!");
        PendingCourseEnrollment enroll = new PendingCourseEnrollment(id, email);
        return ResponseEntity.ok(studentService.enrollCourse(enroll));

    }

    @GetMapping("/course/{id}/timetable")
    public ResponseEntity<?> timetable(@PathVariable String id) {
        Optional<Course> course = courseService.findOne(id);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();
        Resource resource = filesStorageService.loadTimetable(course.get().getTimetable() + ".pdf");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; " + "filename=\" " + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/course/{id}/post")
    public ResponseEntity<?> post(@PathVariable String id) {
        Optional<Course> course = courseService.findOne(id);
        if (course.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(course.get().getPost());
    }

    @PutMapping("/{email}/cv")
    public ResponseEntity<?> updateCv(@PathVariable String email, @RequestBody MultipartFile cv) {
        if (cv == null)
            return ResponseEntity.badRequest().build();
        Optional<Student> student = studentService.findOne(email);
        if (student.isEmpty())
            return ResponseEntity.notFound().build();
        filesStorageService.saveCv(cv, student.get().getEmail() + ".pdf");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getDetails(@PathVariable String email) {
        Optional<Student> student = studentService.findOne(email);
        if (student.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(mapper.map(student.get(), StudentResponse.class));
    }
}

