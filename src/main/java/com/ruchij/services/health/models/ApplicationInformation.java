package com.ruchij.services.health.models;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class ApplicationInformation {
    private String name;
    private String version;
    private String group;
    private String gradleVersion;
    private String gitBranch;
    private String gitCommit;
    private Instant buildTimestamp;
}