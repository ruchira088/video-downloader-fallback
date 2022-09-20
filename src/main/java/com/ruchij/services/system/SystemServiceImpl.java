package com.ruchij.services.system;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Properties;

@Service
public class SystemServiceImpl implements SystemService {
    @Override
    public Properties properties() {
        return System.getProperties();
    }

    @Override
    public Instant timestamp() {
        return Instant.now();
    }

    @Override
    public Map<String, String> environmentVariables() {
        return System.getenv();
    }
}
