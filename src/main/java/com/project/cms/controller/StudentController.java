package com.project.cms.controller;

import com.project.cms.model.Course;
import com.project.cms.model.PendingCourseEnrollment;
import com.project.cms.model.Student;
import com.project.cms.payload.request.CourseRegister;
import com.project.cms.payload.request.StudentRegister;
import com.project.cms.service.ICourseService;
import com.project.cms.service.IFilesStorageService;
import com.project.cms.service.IStudentService;
import com.project.cms.service.StudentService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private IStudentService studentService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IFilesStorageService filesStorageService;
    @Autowired
    private Mapper mapper;
    @Transactional
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {"multipart/form-data"}, produces =
            "application/json")
    public ResponseEntity<?> registerUser(@Valid @ModelAttribute StudentRegister studentRegister) {

        try {
            studentService.register(studentRegister);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/courses")
    public ResponseEntity<?> getAllCourses(){
        return ResponseEntity.ok(courseService.getAll().stream().map(x->mapper.map(x,CourseRegister.class)));
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<?> getDetailsCourse(@PathVariable String id){
        Optional<Course> course= courseService.findOne(id);
        if(course.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(mapper.map(course.get(), CourseRegister.class));
    }

    @PostMapping("/courses/{id}/enroll")
    public ResponseEntity<?> enrollCourse(@PathVariable String id){
        if(courseService.findOne(id).isEmpty())
            return ResponseEntity.notFound().build();
        Object principal= SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!(principal instanceof AnonymousAuthenticationToken)){
            String studentId=((UserDetails) principal).getUsername();
            PendingCourseEnrollment enroll=new PendingCourseEnrollment();
            enroll.setCourseId(id);
            enroll.setStudentEmail(studentId);
           return ResponseEntity.ok(studentService.enroll(enroll));
        }
        else return  ResponseEntity.badRequest().body("You must be logged!");
    }

    @PutMapping("/{id}/update/cv")
    public ResponseEntity<?> updateCv(@PathVariable String id, @RequestBody MultipartFile cv){
        Optional<Student> student= studentService.findOne(id);
        if(student.isEmpty())
            return ResponseEntity.notFound().build();
        if(cv==null)
            return ResponseEntity.badRequest().build();
        filesStorageService.save(cv, student.get().getEmail() + ".pdf");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/courses/{id}/timetable")
    public ResponseEntity<?> timetable(@PathVariable String id)
    {
        Optional<Course> course=courseService.findOne(id);
        if(course.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }

}

