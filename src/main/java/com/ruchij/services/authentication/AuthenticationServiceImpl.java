package com.ruchij.services.authentication;

import com.ruchij.daos.credentials.CredentialsRepository;
import com.ruchij.daos.user.UserRepository;
import com.ruchij.daos.user.models.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(
        AuthenticationManager authenticationManager,
        UserRepository userRepository,
        CredentialsRepository credentialsRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(String email, String password, SecurityContext securityContext) throws AuthenticationException {
        return userRepository.findUserByEmail(email)
            .flatMap(user ->
                credentialsRepository.findCredentialsByUserId(user.getId())
                    .filter(credentials -> passwordEncoder.matches(password, credentials.getHashedPassword()))
                    .map(credentials -> {
                        UsernamePasswordAuthenticationToken authenticationToken =
                            UsernamePasswordAuthenticationToken.authenticated(user, credentials, List.of());

                        authenticationManager.authenticate(authenticationToken);
                        securityContext.setAuthentication(authenticationToken);

                        return user;
                    })
            )
            .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
    }
}
