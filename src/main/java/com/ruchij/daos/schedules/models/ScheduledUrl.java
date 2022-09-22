package com.ruchij.daos.schedules.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Setter
@Getter
@Entity(name = "scheduled_url")
public class ScheduledUrl {
    @Id
    private String id;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String url;

    protected ScheduledUrl() {
    }

    public ScheduledUrl(String id, String url, String userId, Instant createdAt) {
        this.id = id;
        this.url = url;
        this.userId = userId;
        this.createdAt = createdAt;
    }
}
