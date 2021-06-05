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

import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements IStudentService{

    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final IFilesStorageService filesStorageService;
    private final PendingCourseEnrollmentRepository pendingCourseEnrollmentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, BCryptPasswordEncoder encoder, RoleRepository roleRepository, IFilesStorageService iFilesStorageService, PendingCourseEnrollmentRepository pendingCourseEnrollmentRepository) {
        this.studentRepository = studentRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.filesStorageService = iFilesStorageService;
        this.pendingCourseEnrollmentRepository = pendingCourseEnrollmentRepository;
    }

    @Override
    public Student register(StudentRegister studentRegister) throws Exception {
        if (studentRepository.existsByEmail(studentRegister.getEmail()))
            throw new Exception("Email address already used");

        if (studentRegister.getCv().isEmpty())
            throw new Exception("CV is Empty");
        Student student = new Student();
        student.setFName(studentRegister.getFname());
        student.setLName(studentRegister.getLname());
        student.setPhone(studentRegister.getPhone());
        student.setEmail(studentRegister.getEmail());
        student.setPassword(encoder.encode(studentRegister.getPassword()));
        student.setUniversity(studentRegister.getUniversity());
        student.setYear(studentRegister.getYear());
        String fileName = student.getEmail() + ".pdf";
        filesStorageService.saveCv(studentRegister.getCv(), fileName);
        student.setCv(fileName);
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
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public PendingCourseEnrollment enroll(PendingCourseEnrollment courseEnrollment) {
        return pendingCourseEnrollmentRepository.save(courseEnrollment);
    }
}
