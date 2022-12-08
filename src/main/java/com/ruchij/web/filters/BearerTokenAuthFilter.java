package com.ruchij.web.filters;

import com.ruchij.config.AdminAuthConfiguration;
import com.ruchij.daos.user.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Component
public class BearerTokenAuthFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_TYPE = "Bearer";
    private static final String ADMIN_EMAIL = "me@ruchij.com";

    private final UserRepository userRepository;
    private final AdminAuthConfiguration adminAuthConfiguration;
    private final SessionRepository<?> sessionRepository;
    private final Base64.Decoder decoder;

    public BearerTokenAuthFilter(
        UserRepository userRepository,
        SessionRepository<?> sessionRepository,
        AdminAuthConfiguration adminAuthConfiguration
    ) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.adminAuthConfiguration = adminAuthConfiguration;
        this.decoder = Base64.getDecoder();
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        FilterChain filterChain
    ) throws ServletException, IOException {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        if (Optional.ofNullable(securityContext.getAuthentication()).filter(Authentication::isAuthenticated).isEmpty()) {
            bearerToken(httpServletRequest)
                .flatMap(bearerToken ->
                    userAuthentication(bearerToken).or(() -> adminAuthentication(bearerToken))
                )
                .ifPresent(securityContext::setAuthentication);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private Optional<String> bearerToken(HttpServletRequest httpServletRequest) {
        return Optional.ofNullable(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))
            .filter(authenticationHeader -> authenticationHeader.startsWith(AUTHORIZATION_TYPE))
            .map(authenticationHeader -> authenticationHeader.substring(AUTHORIZATION_TYPE.length()).trim())
            .filter(token -> !token.isEmpty());
    }

    private Optional<Authentication> userAuthentication(String bearerToken) {
        try {
            String token = new String(decoder.decode(bearerToken));
            return Optional.ofNullable(sessionRepository.findById(token))
                .flatMap(session ->
                    Optional.ofNullable(
                        session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY)
                    )
                )
                .flatMap(securityContext -> {
                    if (securityContext instanceof SecurityContext) {
                        return Optional.ofNullable(((SecurityContext) securityContext).getAuthentication());
                    } else {
                        return Optional.empty();
                    }
                });
        } catch (IllegalArgumentException illegalArgumentException) {
            return Optional.empty();
        }
    }

    private Optional<Authentication> adminAuthentication(String bearerToken) {
        if (bearerToken.equals(adminAuthConfiguration.getBearerToken())) {
            return userRepository.findUserByEmail(ADMIN_EMAIL)
                .map(user ->
                    UsernamePasswordAuthenticationToken.authenticated(
                        user,
                        null,
                        user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getRoleType().name()))
                            .toList()
                    )
                );
        } else {
            return Optional.empty();
        }
    }
}
