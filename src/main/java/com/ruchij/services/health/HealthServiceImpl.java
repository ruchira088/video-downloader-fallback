package com.ruchij.services.health;

import com.ruchij.services.health.models.ApplicationInformation;
import com.ruchij.services.health.models.HealthCheck;
import com.ruchij.services.health.models.HealthStatus;
import com.ruchij.services.health.models.ServiceInformation;
import com.ruchij.services.system.SystemService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class HealthServiceImpl implements HealthService {

    private final JdbcTemplate jdbcTemplate;
    private final SystemService systemService;
    private final ApplicationInformation applicationInformation;

    public HealthServiceImpl(JdbcTemplate jdbcTemplate, SystemService systemService, ApplicationInformation applicationInformation) {
        this.jdbcTemplate = jdbcTemplate;
        this.systemService = systemService;
        this.applicationInformation = applicationInformation;
    }

    @Override
    public ServiceInformation serviceInformation() {
        String javaVersion = systemService.properties().getProperty("java.version", "unknown");
        Instant instant = systemService.timestamp();

        return new ServiceInformation(
            applicationInformation.getName(),
            applicationInformation.getVersion(),
            applicationInformation.getGroup(),
            javaVersion,
            applicationInformation.getGradleVersion(),
            instant,
            applicationInformation.getGitBranch(),
            applicationInformation.getGitCommit(),
            applicationInformation.getBuildTimestamp()
        );
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
}
