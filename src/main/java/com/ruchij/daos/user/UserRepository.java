package com.ruchij.daos.user;

import com.ruchij.daos.user.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findUserByEmail(String email);
}
