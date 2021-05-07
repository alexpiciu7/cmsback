package com.project.cms.repository;

import com.project.cms.models.Admin;
import com.project.cms.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {
    Admin findByEmail(String email);
    Boolean existsByEmail(String email);

}
