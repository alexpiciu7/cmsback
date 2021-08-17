package com.project.cms.service;

import com.project.cms.model.*;
import com.project.cms.payload.request.StudentRegister;
import com.project.cms.repository.PendingCourseEnrollmentRepository;
import com.project.cms.repository.RoleRepository;
import com.project.cms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements IStudentService{

    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final PendingCourseEnrollmentRepository pendingCourseEnrollmentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, BCryptPasswordEncoder encoder,
                          RoleRepository roleRepository, PendingCourseEnrollmentRepository pendingCourseEnrollmentRepository) {
        this.studentRepository = studentRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.pendingCourseEnrollmentRepository = pendingCourseEnrollmentRepository;
    }

    @Override
    public Student register(StudentRegister studentRegister, MultipartFile cv) throws Exception {
        if (studentRepository.existsByEmail(studentRegister.getEmail()))
            throw new Exception("Email address already used");

        if (cv==null)
            throw new Exception("CV is Empty");
        Student student = new Student();
        student.setFName(studentRegister.getFname());
        student.setLName(studentRegister.getLname());
        student.setPhone(studentRegister.getPhone());
        student.setEmail(studentRegister.getEmail());
        student.setPassword(encoder.encode(studentRegister.getPassword()));
        student.setUniversity(studentRegister.getUniversity());
        student.setYear(studentRegister.getYear());
        student.setCv(Base64.getEncoder().encodeToString(cv.getBytes()));
        Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        student.setRole(userRole);
        student = studentRepository.save(student);
        return student;
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> findOne(String id) {
        if (studentRepository.findByEmail(id)==null)
            return Optional.empty();
        else
            return Optional.of(studentRepository.findByEmail(id));
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public PendingCourseEnrollment enrollCourse(PendingCourseEnrollment courseEnrollment) {
        return pendingCourseEnrollmentRepository.save(courseEnrollment);
    }

}
