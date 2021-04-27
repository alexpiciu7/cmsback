package com.project.cms.service;

import com.project.cms.models.ERole;
import com.project.cms.models.Role;
import com.project.cms.models.Student;
import com.project.cms.payload.request.LoginRequest;
import com.project.cms.payload.request.StudentSignup;
import com.project.cms.repository.*;
import com.project.cms.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class UserService implements IUserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final ManagerRepository managerRepository;
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final IFilesStorageService IFilesStorageService;

    @Autowired
    public UserService(AuthenticationManager authenticationManager, UserRepository userRepository, StudentRepository studentRepository, InstructorRepository instructorRepository, ManagerRepository managerRepository, AdminRepository adminRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder, JwtUtils jwtUtils, com.project.cms.service.IFilesStorageService IFilesStorageService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.managerRepository = managerRepository;
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.IFilesStorageService = IFilesStorageService;
    }

    @Override
    public Student register(StudentSignup studentSignup) throws Exception {

        if (userRepository.existsByEmail(studentSignup.getEmail()))
            throw new Exception("Email address already used");

        if (studentSignup.getCv().isEmpty())
            throw new Exception("CV is Empty");
        Student student = new Student();
        student.setFName(studentSignup.getFname());
        student.setLName(studentSignup.getLname());
        student.setPhone(studentSignup.getPhone());
        student.setEmail(studentSignup.getEmail());
        student.setPassword(encoder.encode(studentSignup.getPassword()));
        student.setUniversity(studentSignup.getUniversity());
        student.setYear(studentSignup.getYear());
        String fileName = UUID.randomUUID().toString() + ".pdf";
        IFilesStorageService.save(studentSignup.getCv(), fileName);
        student.setCv(fileName);
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        student.setRole(userRole);
        student = userRepository.save(student);
        return student;

    }

    @Override
    public Map<Object, Object> login(LoginRequest loginRequest) {
        try {
            String username = loginRequest.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword()));
            String token = jwtUtils.generateJwtToken(username, this.userRepository.findByEmail(username).getRole());
            Map<Object, Object> model = new HashMap<>();
            model.put("email", username);
            model.put("token", token);
            return model;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email/password supplied");
        }
    }
}
