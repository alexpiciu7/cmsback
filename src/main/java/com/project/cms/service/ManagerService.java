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
public class ManagerService implements IManagerService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerService(StudentRepository studentRepository, CourseRepository courseRepository, InstructorRepository instructorRepository, ManagerRepository managerRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.managerRepository = managerRepository;
    }

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
