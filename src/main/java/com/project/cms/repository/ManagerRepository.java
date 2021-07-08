package com.project.cms.repository;

import com.project.cms.model.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends MongoRepository<Manager, String> {
    Manager findByEmail(String email);
    Boolean existsByEmail(String email);

}
