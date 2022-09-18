package com.ruchij.services.user;

import com.ruchij.daos.user.models.User;
import com.ruchij.exceptions.ResourceConflictException;
import com.ruchij.exceptions.ResourceNotFoundException;

import java.util.Optional;

public interface UserService {
    User create(String email, String password, String firstName, String lastName) throws ResourceConflictException;

    User update(String userId, Optional<String> email, Optional<String> firstName, Optional<String> lastName)
        throws ResourceNotFoundException;

    User getById(String userId) throws ResourceNotFoundException;
}
