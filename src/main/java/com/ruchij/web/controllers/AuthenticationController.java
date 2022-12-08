package com.ruchij.web.controllers;

import com.ruchij.daos.user.models.User;
import com.ruchij.web.requests.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/authentication")
@RestController
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final LogoutHandler logoutHandler;

    public AuthenticationController(AuthenticationManager authenticationManager, LogoutHandler logoutHandler) {
        this.authenticationManager = authenticationManager;
        this.logoutHandler = logoutHandler;
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

    @DeleteMapping("/logout")
    public User logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        logoutHandler.logout(httpServletRequest, httpServletResponse, authentication);

        return (User) authentication.getPrincipal();
    }

}
