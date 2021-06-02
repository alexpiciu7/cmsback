package com.project.cms.repository;

import com.project.cms.model.PendingCourseEnrollment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PendingCourseEnrollmentRepository extends MongoRepository<PendingCourseEnrollment,String> {
    Optional <PendingCourseEnrollment> findByCourseIdAndStudentEmail(String courseId, String studentEmail);

}
