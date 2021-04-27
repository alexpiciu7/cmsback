package com.project.cms.service;

import com.project.cms.models.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentService {
    Student save(Student student);
    Optional<Student> findOne(String id);
    List<Student> findAll();

}
