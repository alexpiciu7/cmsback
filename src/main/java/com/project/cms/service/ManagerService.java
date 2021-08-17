package com.project.cms.service;

import com.project.cms.model.Course;
import com.project.cms.model.Instructor;
import com.project.cms.model.Manager;
import com.project.cms.model.Student;
import com.project.cms.repository.CourseRepository;
import com.project.cms.repository.InstructorRepository;
import com.project.cms.repository.ManagerRepository;
import com.project.cms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ManagerService implements IManagerService{
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    @Override
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }
}
