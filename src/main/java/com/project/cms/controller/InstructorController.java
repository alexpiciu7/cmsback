package com.project.cms.controller;

import com.project.cms.model.Course;
import com.project.cms.payload.request.CourseRegister;
import com.project.cms.service.CourseService;
import com.project.cms.service.FilesStorageService;
import com.project.cms.service.InstructorService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.util.Optional;

@RestController
@RequestMapping("/instructor")
public class InstructorController {
    private final CourseService courseService;
    private final InstructorService instructorService;
    private final FilesStorageService filesStorageService;
    private final Mapper mapper;

    @Autowired
    public InstructorController(CourseService courseService, InstructorService instructorService, FilesStorageService filesStorageService, Mapper mapper) {
        this.courseService = courseService;
        this.instructorService = instructorService;
        this.filesStorageService = filesStorageService;
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
        @PutMapping("/deactivate/course/{id}")
    public ResponseEntity<?> deactivateCourse(@PathVariable String id){
            Optional<Course> course= courseService.findOne(id);
            if(course.isEmpty())
                return ResponseEntity.notFound().build();
            course.get().setActive(false);
            return ResponseEntity.ok(courseService.save(course.get()));
        }

    @PutMapping("/activate/course/{id}")
    public ResponseEntity<?> activateCourse(@PathVariable String id){
        Optional<Course> course= courseService.findOne(id);
        if(course.isEmpty())
            return ResponseEntity.notFound().build();
        course.get().setActive(true);
        return ResponseEntity.ok(courseService.save(course.get()));
    }
    @PutMapping("/course/{id}/capacity")
    public ResponseEntity<?> capacity(@PathVariable String id,@RequestBody @Positive int capacity){
        Optional<Course> course= courseService.findOne(id);
        if(course.isEmpty())
            return ResponseEntity.notFound().build();
        course.get().setCapacity(capacity);
        return ResponseEntity.ok(courseService.save(course.get()));
    }
    @PutMapping("/course/{id}/timetable")
    public ResponseEntity<?> timetable(@PathVariable String id, @RequestBody MultipartFile timetable){
        Optional<Course> course= courseService.findOne(id);
        if(course.isEmpty())
            return ResponseEntity.notFound().build();
        filesStorageService.saveTimetable(timetable,course.get().getTimetable());
        return ResponseEntity.ok().build();
    }


    }


