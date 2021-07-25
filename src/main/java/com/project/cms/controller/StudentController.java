package com.project.cms.controller;

import com.project.cms.model.*;
import com.project.cms.payload.request.CourseRegister;
import com.project.cms.payload.request.StudentRegister;
import com.project.cms.repository.GroupRepository;
import com.project.cms.repository.PendingGroupEnrollmentRepository;
import com.project.cms.service.ICourseService;
import com.project.cms.service.IFilesStorageService;
import com.project.cms.service.IStudentService;
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
import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/student")
public class StudentController {

    private final IStudentService studentService;
    private final ICourseService courseService;
    private final IFilesStorageService filesStorageService;
    private final Mapper mapper;
    private final GroupRepository groupRepository;


    @Autowired
    public StudentController(IStudentService studentService, ICourseService courseService, IFilesStorageService filesStorageService, Mapper mapper, GroupRepository groupRepository, PendingGroupEnrollmentRepository pendingGroupEnrollmentRepository) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.filesStorageService = filesStorageService;
        this.mapper = mapper;
        this.groupRepository = groupRepository;

    }

    @Transactional
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {"multipart/form-data"}, produces =
            "application/json")
    public ResponseEntity<?> registerUser(@ModelAttribute StudentRegister studentRegister) {

        try {
            studentService.register(studentRegister,studentRegister.getCv());
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getCause());
        }
    }
    @GetMapping("/course/all")
    public ResponseEntity<?> getAllCourses(){
        return ResponseEntity.ok(courseService.getAll().stream().map(x->mapper.map(x,CourseRegister.class)));
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<?> getDetailsCourse(@PathVariable String id){
        Optional<Course> course= courseService.findOne(id);
        if(course.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(mapper.map(course.get(), CourseRegister.class));
    }

    @PostMapping("/course/{id}/enroll")
    public ResponseEntity<?> enrollCourse(@PathVariable String id){
        Optional<Course> course=courseService.findOne(id);
        if(course.isEmpty())
            return ResponseEntity.notFound().build();
        if(!course.get().isActive())
            return ResponseEntity.badRequest().body("Course is not active!");
        if(!(course.get().getRegisterDuration().getStartDate().after(new Date())&&course.get().getRegisterDuration().getEndDate().before(new Date())))
            return ResponseEntity.badRequest().body("Wrong date!");
        Object principal= SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!(principal instanceof AnonymousAuthenticationToken)){
            PendingCourseEnrollment enroll=new PendingCourseEnrollment(id,((UserDetails) principal).getUsername());
            return ResponseEntity.ok(studentService.enrollCourse(enroll));
        }
        else return  ResponseEntity.badRequest().body("You must be logged!");
    }

    @PostMapping("/course/{idCourse}/group/{groupNo}/enroll")
    public ResponseEntity<?> enrollGroup(@PathVariable String idCourse, @PathVariable String groupNo){
        Optional<Course> course=courseService.findOne(idCourse);
        Optional<Group> group=groupRepository.findByGroupNo(groupNo);
        if(course.isEmpty())
            return ResponseEntity.notFound().build();
        if(group.isEmpty())
            return ResponseEntity.notFound().build();
        Object principal= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(principal instanceof AnonymousAuthenticationToken)){
            PendingGroupEnrollment enroll=new PendingGroupEnrollment(idCourse,groupNo,((UserDetails) principal).getUsername());
            return ResponseEntity.ok(studentService.enrollGroup(enroll));
        }
        else return  ResponseEntity.badRequest().body("You must be logged!");
    }

    @GetMapping("/course/{id}/timetable")
    public ResponseEntity<?> timetable(@PathVariable String id)
    {
        Optional<Course> course=courseService.findOne(id);
        if(course.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(filesStorageService.loadTimetable(course.get().getTimetable())+".pdf");
    }

    @GetMapping("/course/{id}/post")
    public ResponseEntity<?> post(@PathVariable String id)
    {
        Optional<Course> course=courseService.findOne(id);
        if(course.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(course.get().getPost());
    }

    @PutMapping("/{email}/update/cv")
    public ResponseEntity<?> updateCv(@PathVariable String email, @RequestBody MultipartFile cv){
        Optional<Student> student= studentService.findOne(email);
        if(student.isEmpty())
            return ResponseEntity.notFound().build();
        if(cv==null)
            return ResponseEntity.badRequest().build();
        filesStorageService.saveCv(cv, student.get().getEmail() + ".pdf");
        return ResponseEntity.ok().build();
    }

}

