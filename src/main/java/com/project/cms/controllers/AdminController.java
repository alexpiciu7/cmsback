package com.project.cms.controllers;

import com.project.cms.payload.request.InstructorRegister;
import com.project.cms.service.IAdminService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final IAdminService adminService;

    @Autowired
    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register/instructor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerInstructor(@RequestBody @Valid InstructorRegister instructorRegister) {
        try {
            adminService.registerInstructor(instructorRegister);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
