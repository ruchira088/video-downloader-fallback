package com.ruchij.services.health;

import com.ruchij.services.health.models.HealthCheck;
import com.ruchij.services.health.models.ServiceInformation;

public interface HealthService {
    ServiceInformation serviceInformation();

    HealthCheck healthCheck();
}
