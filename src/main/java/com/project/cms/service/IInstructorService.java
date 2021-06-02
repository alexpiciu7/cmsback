package com.project.cms.service;

import com.project.cms.model.Course;
import com.project.cms.model.Student;
import com.project.cms.payload.request.CourseRegister;

import java.util.List;

public interface IInstructorService {
    List<Student> getAllStudents();
}
