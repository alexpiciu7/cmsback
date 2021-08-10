package com.project.cms.service;

import com.project.cms.model.Course;
import com.project.cms.model.Group;

import java.util.List;
import java.util.Optional;

public interface ICourseService {
    Course save(Course course);
    List<Course> getAll();
    Optional<Course> findOne(String id);
//    List<Group> getGroups(String courseId);
//   // int totalGroupParticipants(String courseId);
//    Group saveGroup(Group group);
}
