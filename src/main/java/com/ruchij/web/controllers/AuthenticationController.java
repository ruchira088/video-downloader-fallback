package com.ruchij.web.controllers;

import com.ruchij.daos.user.models.User;
import com.ruchij.services.authentication.AuthenticationService;
import com.ruchij.web.requests.LoginRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest loginRequest) {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        return authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword(), securityContext);
    }

    @GetMapping("/user")
    public User user() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
