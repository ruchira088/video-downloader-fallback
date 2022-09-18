package com.ruchij.services.health;

import com.ruchij.services.health.models.HealthCheck;
import com.ruchij.services.health.models.ServiceInformation;

import java.io.IOException;

public interface HealthService {
    ServiceInformation serviceInformation() throws IOException;

    HealthCheck healthCheck();
}
