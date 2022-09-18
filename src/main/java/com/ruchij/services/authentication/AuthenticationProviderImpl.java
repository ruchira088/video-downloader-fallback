package com.ruchij.services.authentication;

import com.ruchij.daos.credentials.CredentialsRepository;
import com.ruchij.daos.user.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationProviderImpl implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationProviderImpl(
        UserRepository userRepository,
        CredentialsRepository credentialsRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        return userRepository.findUserByEmail(email)
            .flatMap(user ->
                credentialsRepository.findCredentialsByUserId(user.getId())
                    .filter(credentials -> passwordEncoder.matches(password, credentials.getHashedPassword()))
                    .map(credentials -> UsernamePasswordAuthenticationToken.authenticated(user, credentials, List.of()))
            )
            .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
