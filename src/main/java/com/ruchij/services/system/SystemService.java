package com.ruchij.services.system;

import java.util.Map;
import java.util.Properties;

public interface SystemService {
    Properties properties();

    Map<String, String> environmentVariables();
}
