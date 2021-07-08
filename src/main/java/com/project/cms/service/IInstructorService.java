package com.project.cms.service;

import com.project.cms.model.Instructor;
import com.project.cms.model.Student;

import java.util.List;
import java.util.Optional;

public interface IInstructorService {
    List<Student> getAllStudents();

    Optional<Instructor> findOne(String id);

    List<Instructor> getAllInstructor();
}
