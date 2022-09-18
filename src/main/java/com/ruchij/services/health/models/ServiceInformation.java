package com.ruchij.services.health.models;

import java.time.Instant;

public record ServiceInformation(String serviceName, String serviceVersion, String organization, String javaVersion,
                                 String gradleVersion, Instant currentTimestamp, String gitBranch, String gitCommit,
                                 Instant buildTimestamp) {
}
