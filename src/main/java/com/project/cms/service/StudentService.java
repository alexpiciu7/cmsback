package com.project.cms.service;

import com.project.cms.model.ERole;
import com.project.cms.model.PendingCourseEnrollment;
import com.project.cms.model.Role;
import com.project.cms.model.Student;
import com.project.cms.payload.request.StudentRegister;
import com.project.cms.repository.PendingCourseEnrollmentRepository;
import com.project.cms.repository.RoleRepository;
import com.project.cms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Service
public class StudentService implements IStudentService {

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

        if (cv == null)
            throw new Exception("CV is Empty");
        Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Student student = new Student(studentRegister, Base64.getEncoder().encodeToString(cv.getBytes()), encoder.encode(studentRegister.getPassword()), userRole);
        student = studentRepository.save(student);
        return student;
    }

    @Override
    public Optional<Student> findOne(String id) {
        if (studentRepository.findByEmail(id) == null)
            return Optional.empty();
        else
            return Optional.of(studentRepository.findByEmail(id));
    }

    @Override
    public PendingCourseEnrollment enrollCourse(PendingCourseEnrollment courseEnrollment) {
        return pendingCourseEnrollmentRepository.save(courseEnrollment);
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

}
