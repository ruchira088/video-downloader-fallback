package com.ruchij.daos.authorization;

import com.ruchij.daos.authorization.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends CrudRepository<Role, String> {
}
