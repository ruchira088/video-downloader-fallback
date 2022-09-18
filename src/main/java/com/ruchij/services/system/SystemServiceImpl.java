package com.ruchij.services.system;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Properties;

@Service
public class SystemServiceImpl implements SystemService {
    @Override
    public Properties properties() {
        return System.getProperties();
    }

    @Override
    public Map<String, String> environmentVariables() {
        return System.getenv();
    }
}
