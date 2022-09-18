package com.ruchij.web.controllers;

import com.ruchij.daos.user.models.User;
import com.ruchij.web.requests.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/authentication")
@RestController
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public User login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication =
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return (User) authentication.getPrincipal();
    }

    @GetMapping("/user")
    public User user() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
