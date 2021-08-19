package com.project.cms.service;

import com.project.cms.model.Course;
import com.project.cms.model.Instructor;
import com.project.cms.model.Manager;
import com.project.cms.model.Student;

import java.util.List;

public interface IManagerService {
    List<Student> getAllStudents();
    List<Course> getAllCourses();
    List<Instructor> getAllInstructors();
    List<Manager> getAllManagers();
}
