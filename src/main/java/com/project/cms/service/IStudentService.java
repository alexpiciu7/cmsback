package com.project.cms.service;

import com.project.cms.model.Student;
import com.project.cms.payload.request.StudentRegister;

import java.util.List;
import java.util.Optional;

public interface IStudentService {
    Student register(StudentRegister studentRegister) throws Exception;
    Student save(Student student);
    Optional<Student> findOne(String id);
    List<Student> findAll();

}
