package com.project.cms.controllers;

import com.project.cms.payload.request.StudentRegister;
import com.project.cms.repository.AdminRepository;
import com.project.cms.repository.RoleRepository;
import com.project.cms.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private IStudentService studentService;

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

}

