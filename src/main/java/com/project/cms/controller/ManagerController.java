package com.project.cms.controller;

import com.project.cms.payload.response.InstructorResponse;
import com.project.cms.payload.response.ManagerResponse;
import com.project.cms.payload.response.StudentResponse;
import com.project.cms.service.ManagerService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/manager")
public class ManagerController {
    private final ManagerService managerService;
    private final Mapper mapper;

    @Autowired
    public ManagerController(ManagerService managerService, Mapper mapper) {
        this.managerService = managerService;
        this.mapper = mapper;
    }

    //DONE
    @GetMapping("/get/instructors")
    public ResponseEntity<?> allInstructors() {
        return ResponseEntity.ok(managerService.getAllInstructors().stream().map(x -> mapper.map(x, InstructorResponse.class)).collect(Collectors.toList()));
    }

    //DONE
    @GetMapping("/get/managers")
    public ResponseEntity<?> allManagers() {
        return ResponseEntity.ok(managerService.getAllManagers().stream().map(x -> mapper.map(x, ManagerResponse.class)).collect(Collectors.toList()));
    }

    //DONE
    @GetMapping("/get/students")
    public ResponseEntity<?> allStudents() {
        return ResponseEntity.ok(managerService.getAllStudents().stream().map(x -> mapper.map(x, StudentResponse.class)).collect(Collectors.toList()));
    }

    //DONE
    @GetMapping("/get/courses")
    public ResponseEntity<?> allCourses() {
        return ResponseEntity.ok(managerService.getAllCourses());
    }
}
