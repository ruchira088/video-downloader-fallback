package com.ruchij.services.authentication;

import com.ruchij.daos.user.models.User;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;

public interface AuthenticationService {
    User login(String email, String password, SecurityContext securityContext) throws AuthenticationException;
}
