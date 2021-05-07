package com.project.cms.service;

import com.project.cms.models.Instructor;
import com.project.cms.models.Manager;
import com.project.cms.payload.request.InstructorRegister;
import com.project.cms.payload.request.ManagerRegister;
import com.project.cms.repository.AdminRepository;
import com.project.cms.repository.InstructorRepository;
import com.project.cms.repository.ManagerRepository;
import com.project.cms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements IAdminService{

    private AdminRepository adminRepository;
    private StudentRepository studentRepository;
    private ManagerRepository managerRepository;
    private InstructorRepository instructorRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, StudentRepository studentRepository, ManagerRepository managerRepository, InstructorRepository instructorRepository) {
        this.adminRepository = adminRepository;
        this.studentRepository = studentRepository;
        this.managerRepository = managerRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public Manager registerManager(ManagerRegister managerRegister) {
        return null;
    }

    @Override
    public Instructor registerInstructor(InstructorRegister instructorRegister) {
        return null;
    }

    @Override
    public boolean activateManagerAccount(String id) {
        return false;
    }

    @Override
    public boolean activateInstructorAccount(String id) {
        return false;
    }

    @Override
    public boolean deactivateManagerAccount(String id) {
        return false;
    }

    @Override
    public boolean deactivateInstructorAccount(String id) {
        return false;
    }

    @Override
    public boolean activateStudentAccount(String id) {
        return false;
    }

    @Override
    public boolean deactivateStudentAccount(String id) {
        return false;
    }
}
