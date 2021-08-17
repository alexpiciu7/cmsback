package com.project.cms.repository;

import com.project.cms.model.PendingCourseEnrollment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PendingCourseEnrollmentRepository extends MongoRepository<PendingCourseEnrollment,String> {
    Optional <PendingCourseEnrollment> findByCourseIdAndStudentEmail(String courseId, String studentEmail);

    @Query(value = "{ 'courseId' : {'$in' : ?0 } }")
    List<PendingCourseEnrollment> findAllWithIdIn(List<String> ids);

}
