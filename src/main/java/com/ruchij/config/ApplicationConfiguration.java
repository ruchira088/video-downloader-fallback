package com.ruchij.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruchij.services.health.models.ApplicationInformation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class ApplicationConfiguration {
    private static final String INFORMATION_FILE = "information.json";

    @Bean
    public ApplicationInformation applicationInformation(ObjectMapper objectMapper) throws Exception {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INFORMATION_FILE)) {
            return objectMapper.readValue(inputStream, ApplicationInformation.class);
        }
    }
}
