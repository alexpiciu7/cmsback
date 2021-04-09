package com.project.cms.repository;

import com.project.cms.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String> { ;

    Boolean existsByEmail(String email);

    User findByEmail(String email);
}
