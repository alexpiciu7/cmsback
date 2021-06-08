package com.project.cms.repository;

import com.project.cms.model.PendingGroupEnrollment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PendingGroupEnrollmentRepository extends MongoRepository<PendingGroupEnrollment, String> {
    Optional<PendingGroupEnrollment> findByCourseIdAndGroupNoAndStudentEmail(String courseId, String groupNo, String studentEmail);
}
