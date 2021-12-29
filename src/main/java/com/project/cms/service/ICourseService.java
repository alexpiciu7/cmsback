package com.project.cms.service;

import com.project.cms.model.Course;
import java.util.List;
import java.util.Optional;

public interface ICourseService {
    Course save(Course course);
    List<Course> getAll();
    Optional<Course> findOne(String id);

}
