package com.project.cms.controller;

import com.project.cms.model.PendingCourseEnrollment;
import com.project.cms.payload.request.StudentRegister;
import com.project.cms.service.ICourseService;
import com.project.cms.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private IStudentService studentService;
    @Autowired
    private ICourseService courseService;
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
        return ResponseEntity.ok(courseService.getAll());
    }

    @PostMapping("/enroll/course/{id}")
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

}

