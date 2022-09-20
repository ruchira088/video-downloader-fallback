package com.ruchij.services.health;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruchij.services.health.models.HealthCheck;
import com.ruchij.services.health.models.HealthStatus;
import com.ruchij.services.health.models.ServiceInformation;
import com.ruchij.services.system.SystemService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class HealthServiceImpl implements HealthService {
    private static final String INFORMATION_FILE = "information.json";

    private final JdbcTemplate jdbcTemplate;
    private final SystemService systemService;
    private final ObjectMapper objectMapper;

    public HealthServiceImpl(JdbcTemplate jdbcTemplate, SystemService systemService, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.systemService = systemService;
        this.objectMapper = objectMapper;
    }

    @Override
    public ServiceInformation serviceInformation() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INFORMATION_FILE)) {
            ApplicationInformation applicationInformation = objectMapper.readValue(inputStream, ApplicationInformation.class);
            String javaVersion = systemService.properties().getProperty("java.version", "unknown");
            Instant instant = systemService.timestamp();

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
        CompletableFuture<HealthStatus> databaseHealthCheck = CompletableFuture.supplyAsync(this::database);
        HealthStatus database = waitUntilTimeout(databaseHealthCheck);

        return new HealthCheck(database);
    }

    private HealthStatus database() {
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        return result == 1 ? HealthStatus.HEALTHY : HealthStatus.UNHEALTHY;
    }

    private HealthStatus waitUntilTimeout(CompletableFuture<HealthStatus> healthStatus) {
        try {
            return healthStatus.get(10, TimeUnit.SECONDS);
        } catch (Exception exception) {
            return HealthStatus.UNHEALTHY;
        }
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
