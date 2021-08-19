package com.project.cms.service;

import com.project.cms.model.*;
import com.project.cms.repository.AdminRepository;
import com.project.cms.repository.InstructorRepository;
import com.project.cms.repository.ManagerRepository;
import com.project.cms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private AdminRepository adminRepository;
    private StudentRepository studentRepository;
    private ManagerRepository managerRepository;
    private InstructorRepository instructorRepository;

    @Autowired
    public void setAdminRepository(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    public void setManagerRepository(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Autowired
    public void setInstructorRepository(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = new User();
        Optional<Admin> admin = Optional.ofNullable(adminRepository.findByEmail(email));
        if (admin.isPresent()) {
            user.setEmail(admin.get().getEmail());
            user.setPassword(admin.get().getPassword());
            user.setRole(admin.get().getRole());
        } else {
            Optional<Manager> manager = Optional.ofNullable(managerRepository.findByEmail(email));
            if (manager.isPresent()) {
                user.setEmail(manager.get().getEmail());
                user.setPassword(manager.get().getPassword());
                user.setRole(manager.get().getRole());
            } else {
                Optional<Student> student = Optional.ofNullable(studentRepository.findByEmail(email));
                if (student.isPresent()) {
                    user.setEmail(student.get().getEmail());
                    user.setPassword(student.get().getPassword());
                    user.setRole(student.get().getRole());
                } else {
                    Optional<Instructor> instructor = Optional.ofNullable(instructorRepository.findByEmail(email));
                    if (instructor.isPresent()) {
                        user.setEmail(instructor.get().getEmail());
                        user.setPassword(instructor.get().getPassword());
                        user.setRole(instructor.get().getRole());
                    }
                }

            }
        }
        GrantedAuthority authorities = getUserAuthority(user.getRole());
        return buildUserForAuthentication(user, authorities);
    }

    private GrantedAuthority getUserAuthority(Role userRole) {
        GrantedAuthority role;
        role = new SimpleGrantedAuthority(userRole.getName().name());
        return role;
    }

    private UserDetails buildUserForAuthentication(User user, GrantedAuthority authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.singleton(authorities));
    }

}
