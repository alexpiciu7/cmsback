package com.project.cms.repository;

import java.util.Optional;

import com.project.cms.model.ERole;
import com.project.cms.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
