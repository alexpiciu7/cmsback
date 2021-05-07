package com.project.cms.service;

import com.project.cms.models.ERole;
import com.project.cms.models.Instructor;
import com.project.cms.models.Manager;
import com.project.cms.models.Student;
import com.project.cms.payload.request.InstructorRegister;
import com.project.cms.payload.request.ManagerRegister;
import com.project.cms.repository.*;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    private final StudentRepository studentRepository;
    private final ManagerRepository managerRepository;
    private final InstructorRepository instructorRepository;
    private final RoleRepository roleRepository;
    private final Mapper mapper;

    @Autowired
    public AdminService(StudentRepository studentRepository, ManagerRepository managerRepository, InstructorRepository instructorRepository, RoleRepository roleRepository, Mapper mapper) {

        this.studentRepository = studentRepository;
        this.managerRepository = managerRepository;
        this.instructorRepository = instructorRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }


    @Override
    public Manager registerManager(ManagerRegister managerRegister) {
        Manager manager = mapper.map(managerRegister, Manager.class);
        manager.setRole(roleRepository.findByName(ERole.ROLE_MANAGER).get());
        manager = managerRepository.save(manager);
        return manager;
    }

    @Override
    public Instructor registerInstructor(InstructorRegister instructorRegister) {
        Instructor instructor = mapper.map(instructorRegister, Instructor.class);
        instructor.setRole(roleRepository.findByName(ERole.ROLE_INSTRUCTOR).get());
        instructor = instructorRepository.save(instructor);
        return instructor;
    }

    @Override
    public boolean activateManagerAccount(String id) {
        Optional<Manager> manager = managerRepository.findById(id);
        if (manager.isEmpty()) {
            return false;
        }
        Manager manager1 = manager.get();
        if (manager1.isActive()) return false;
        manager1.setActive(true);
        managerRepository.save(manager1);
        return true;
    }

    @Override
    public boolean activateInstructorAccount(String id) {
        Optional<Instructor>  instructor = instructorRepository.findById(id);
        if (instructor.isEmpty()) {
            return false;
        }
        Instructor instructor1 = instructor.get();
        if (instructor1.isActive()) return false;
        instructor1.setActive(true);
        instructorRepository.save(instructor1);
        return true;
    }

    @Override
    public boolean activateStudentAccount(String id) {
        Optional<Student>  student = studentRepository.findById(id);
        if (student.isEmpty()) {
            return false;
        }
        Student student1 = student.get();
        if (student1.isActive()) return false;
        student1.setActive(true);
        studentRepository.save(student1);
        return true;
    }

    @Override
    public boolean deactivateManagerAccount(String id) {

        Optional<Manager> manager = managerRepository.findById(id);
        if (manager.isEmpty()) {
            return false;
        }
        Manager manager1 = manager.get();
        if (!manager1.isActive()) return false;
        manager1.setActive(false);
        managerRepository.save(manager1);
        return true;
    }

    @Override
    public boolean deactivateInstructorAccount(String id) {
        Optional<Instructor>  instructor = instructorRepository.findById(id);
        if (instructor.isEmpty()) {
            return false;
        }
        Instructor instructor1 = instructor.get();
        if (!instructor1.isActive()) return false;
        instructor1.setActive(false);
        instructorRepository.save(instructor1);
        return true;
    }


    @Override
    public boolean deactivateStudentAccount(String id) {
        Optional<Student>  student = studentRepository.findById(id);
        if (student.isEmpty()) {
            return false;
        }
        Student student1 = student.get();
        if (!student1.isActive()) return false;
        student1.setActive(false);
        studentRepository.save(student1);
        return true;
    }
}
