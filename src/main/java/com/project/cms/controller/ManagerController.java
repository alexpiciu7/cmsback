package com.project.cms.controller;

import com.project.cms.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerController {
   private final ManagerService managerService;

   @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }
    @GetMapping("/get/instructors")
    public ResponseEntity<?> allInstructors(){
       return ResponseEntity.ok(managerService.getAllInstructors());
    }
    @GetMapping("/get/students")
    public ResponseEntity<?> allStudents(){
       return ResponseEntity.ok(managerService.getAllStudents());
    }
    @GetMapping("/get/courses")
    public  ResponseEntity<?> allCourses(){
       return ResponseEntity.ok(managerService.getAllCourses());
    }
}
