package com.project.cms.repository;

import com.project.cms.models.Student;
import com.project.cms.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<Student, String>{
    Student findByEmail(String email);
    Boolean existsByEmail(String email);

}
