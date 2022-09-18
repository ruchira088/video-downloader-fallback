package com.ruchij.services.health;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruchij.services.clock.Clock;
import com.ruchij.services.health.models.HealthCheck;
import com.ruchij.services.health.models.ServiceInformation;
import com.ruchij.services.system.SystemService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;

@Service
public class HealthServiceImpl implements HealthService {
    private static final String INFORMATION_FILE = "information.json";

    private final SystemService systemService;
    private final Clock clock;
    private final ObjectMapper objectMapper;

    public HealthServiceImpl(SystemService systemService, Clock clock, ObjectMapper objectMapper) {
        this.systemService = systemService;
        this.clock = clock;
        this.objectMapper = objectMapper;
    }

    @Override
    public ServiceInformation serviceInformation() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INFORMATION_FILE)) {
            ApplicationInformation applicationInformation = objectMapper.readValue(inputStream, ApplicationInformation.class);
            String javaVersion = systemService.properties().getProperty("java.version", "unknown");
            Instant instant = clock.timestamp();

            return new ServiceInformation(
                applicationInformation.name,
                applicationInformation.version,
                applicationInformation.group,
                javaVersion,
                applicationInformation.gradleVersion,
                instant,
                applicationInformation.gitBranch,
                applicationInformation.gitCommit,
                applicationInformation.buildTimestamp
            );
        }
    }

    @Override
    public HealthCheck healthCheck() {
        return null;
    }

    @Setter
    @Getter
    public static class ApplicationInformation {
        private String name;
        private String version;
        private String group;
        private String gradleVersion;
        private String gitBranch;
        private String gitCommit;
        private Instant buildTimestamp;
    }
}
