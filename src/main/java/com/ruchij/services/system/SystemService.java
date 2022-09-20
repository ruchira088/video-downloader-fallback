package com.ruchij.services.system;

import java.time.Instant;
import java.util.Map;
import java.util.Properties;

public interface SystemService {
    Properties properties();

    Instant timestamp();

    Map<String, String> environmentVariables();
}
