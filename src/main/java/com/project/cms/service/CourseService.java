package com.project.cms.service;

import com.project.cms.model.Course;
import com.project.cms.model.Group;
import com.project.cms.repository.CourseRepository;
import com.project.cms.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements ICourseService{
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    @Autowired
    public CourseService(CourseRepository courseRepository, GroupRepository groupRepository) {
        this.courseRepository = courseRepository;
        this.groupRepository = groupRepository;
    }


    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> findOne(String id) {
        return courseRepository.findById(id);
    }

    @Override
    public List<Group> getGroups(String courseId) {
        return groupRepository.findByCourseId(courseId);
    }

    @Override
    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Optional<Group> findByCourseIdAndGroupNo(String courseId, String groupNo) {
        return groupRepository.findByCourseIdAndGroupNo(courseId,groupNo);
    }

//    @Override
//    public int totalGroupParticipants(String courseId) {
//        return groupRepository.getGroupsTotal(courseId);
//    }
}
