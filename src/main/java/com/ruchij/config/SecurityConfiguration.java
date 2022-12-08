package com.ruchij.config;

import com.ruchij.web.filters.BearerTokenAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider) {
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, BearerTokenAuthFilter bearerTokenAuthFilter) throws Exception {
        httpSecurity.addFilterBefore(bearerTokenAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity
            .cors().and()
            .authorizeHttpRequests(requests ->
                requests
                    .requestMatchers(HttpMethod.POST, "/user", "/authentication/login").permitAll()
                    .requestMatchers(HttpMethod.GET, "/service/**").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(formLoginConfigurer ->
                formLoginConfigurer
                    .defaultSuccessUrl("/authentication/user", true)
                    .permitAll()
            )
            .csrf().disable()
            .build();
    }

    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry corsRegistry) {
                corsRegistry.addMapping("/**").allowedOriginPatterns("**").allowCredentials(true);
            }
        };
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return new SecurityContextLogoutHandler();
    }
}
