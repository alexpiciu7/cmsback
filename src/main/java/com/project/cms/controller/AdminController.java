package com.project.cms.controller;

import com.project.cms.exception.CustomException;
import com.project.cms.model.Admin;
import com.project.cms.model.ERole;
import com.project.cms.payload.request.InstructorRegister;
import com.project.cms.payload.request.ManagerRegister;
import com.project.cms.repository.AdminRepository;
import com.project.cms.repository.RoleRepository;
import com.project.cms.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<?> registerInstructor(@RequestBody InstructorRegister instructorRegister) {
        try {
            adminService.registerInstructor(instructorRegister);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register/manager")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerManager(@RequestBody @Valid ManagerRegister managerRegister) {
        try {
            adminService.registerManager(managerRegister);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/activate/manager/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> activateManagerAccount(@PathVariable String email) {
        try {
            adminService.activateManagerAccount(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(HttpStatus.valueOf(e.getExceptionId()));
        }
    }

    @PutMapping("/activate/instructor/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> activateInstructorAccount(@PathVariable String email) {
        try {
            adminService.activateInstructorAccount(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(HttpStatus.valueOf(e.getExceptionId()));
        }
    }

    @PutMapping("/activate/student/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> activateStudentAccount(@PathVariable String email) {
        try {
            adminService.activateStudentAccount(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(HttpStatus.valueOf(e.getExceptionId()));
        }
    }

    @PutMapping("/deactivate/manager/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactivateManagerAccount(@PathVariable String email) {
        try {
            adminService.deactivateManagerAccount(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(HttpStatus.valueOf(e.getExceptionId()));
        }
    }

    @PutMapping("/deactivate/instructor/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactivateInstructorAccount(@PathVariable String id) {
        try {
            adminService.deactivateInstructorAccount(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(HttpStatus.valueOf(e.getExceptionId()));
        }
    }

    @PutMapping("/deactivate/student/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactivateStudentAccount(@PathVariable String email) {
        try {
            adminService.deactivateStudentAccount(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(HttpStatus.valueOf(e.getExceptionId()));
        }
    }
    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AdminRepository adminRepository;
    @PostMapping("/add/admin")
    public void add(){
        Admin admin=new Admin();
        admin.setFName("Admin");
        admin.setLName("Admin");
        admin.setPhone("0712345678");
        admin.setEmail("admin@admin.com");
        admin.setPassword(encoder.encode("admin1234"));
        admin.setRole(roleRepository.findByName(ERole.ROLE_ADMIN).get());
        adminRepository.save(admin);
 }

}
