package com.ruchij.web.filters;

import com.ruchij.config.AdminAuthConfiguration;
import com.ruchij.daos.user.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class AdminAuthFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_TYPE = "Bearer";
    private static final String ADMIN_EMAIL = "me@ruchij.com";

    private final UserRepository userRepository;
    private final AdminAuthConfiguration adminAuthConfiguration;

    public AdminAuthFilter(UserRepository userRepository, AdminAuthConfiguration adminAuthConfiguration) {
        this.userRepository = userRepository;
        this.adminAuthConfiguration = adminAuthConfiguration;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        FilterChain filterChain
    ) throws ServletException, IOException {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        if (Optional.ofNullable(securityContext.getAuthentication()).filter(Authentication::isAuthenticated).isEmpty()) {
            authenticate(httpServletRequest).ifPresent(securityContext::setAuthentication);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private Optional<Authentication> authenticate(HttpServletRequest httpServletRequest) {
        return Optional.ofNullable(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))
            .filter(authenticationHeader -> authenticationHeader.startsWith(AUTHORIZATION_TYPE))
            .map(authenticationHeader -> authenticationHeader.substring(AUTHORIZATION_TYPE.length()).trim())
            .filter(authenticationToken -> authenticationToken.equals(adminAuthConfiguration.getBearerToken()))
            .flatMap(authenticationToken -> userRepository.findUserByEmail(ADMIN_EMAIL))
            .map(user ->
                UsernamePasswordAuthenticationToken.authenticated(
                    user,
                    null,
                    user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleType().name()))
                        .toList())
            );
    }


}
