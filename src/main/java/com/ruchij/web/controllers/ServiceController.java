package com.ruchij.web.controllers;

import com.ruchij.services.health.HealthService;
import com.ruchij.services.health.models.HealthCheck;
import com.ruchij.services.health.models.ServiceInformation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/service")
@RestController
public class ServiceController {
    private final HealthService healthService;

    public ServiceController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping("/info")
    public ServiceInformation information() throws IOException {
        return healthService.serviceInformation();
    }

    @GetMapping("/health")
    public HealthCheck healthCheck() {
        return healthService.healthCheck();
    }
}
