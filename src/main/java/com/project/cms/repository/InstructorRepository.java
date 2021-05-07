package com.project.cms.repository;

import com.project.cms.models.Instructor;
import com.project.cms.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends MongoRepository<Instructor, String> {
    Instructor findByEmail(String email);
    Boolean existsByEmail(String email);

}
