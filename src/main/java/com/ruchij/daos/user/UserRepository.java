package com.ruchij.daos.user;

import com.ruchij.daos.user.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findUserByEmail(String email);

    Boolean existsUserByEmail(String email);
}
