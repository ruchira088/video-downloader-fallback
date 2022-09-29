package com.ruchij.daos.schedules.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Setter
@Getter
@Entity(name = "scheduled_url")
public class ScheduledUrl {
    @Id
    private String id;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @JsonIgnore
    @Column(nullable = false)
    private Instant updatedAt;

    @Column(nullable = false, updatable = false)
    private String userId;

    @Column(nullable = false, updatable = false)
    private String url;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    protected ScheduledUrl() {
    }

    public ScheduledUrl(String id, String url, String userId, Instant timestamp, Status status) {
        this.id = id;
        this.url = url;
        this.userId = userId;
        this.createdAt = timestamp;
        this.updatedAt = timestamp;
        this.status = status;
    }
}
