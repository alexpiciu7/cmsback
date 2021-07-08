package com.project.cms.repository;

import com.project.cms.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends MongoRepository<Group,String> {
Optional<Group> findByGroupNo(String groupNo);
List<Group> findByCourseId(String courseId);
//@Query("{$match: {courseId: ?0}, total:{$sum: $capacity} }")
//int getGroupsTotal(String courseId);
    Optional<Group> findByCourseIdAndGroupNo(String courseId,String groupNo);
}
