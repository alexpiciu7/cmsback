package com.project.cms.controller;

import com.project.cms.model.Course;
import com.project.cms.payload.request.CourseRegister;
import com.project.cms.service.CourseService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructor")
public class InstructorController {
    private final CourseService courseService;
    private final Mapper mapper;

    @Autowired
    public InstructorController(CourseService courseService, Mapper mapper) {
        this.courseService = courseService;
        this.mapper = mapper;
    }
    @PostMapping("/add/course")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity <?> addCourse(@RequestBody CourseRegister courseRegister){
        Course course= mapper.map(courseRegister,Course.class);
        try{
            courseService.save(course);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception E) {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        }

    }


