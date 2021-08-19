package com.project.cms.service;

import com.project.cms.model.Instructor;
import com.project.cms.model.Student;

import java.util.List;
import java.util.Optional;

public interface IInstructorService {

    Optional<Instructor> findOne(String id);

}
