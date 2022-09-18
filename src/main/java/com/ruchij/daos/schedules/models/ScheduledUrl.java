package com.ruchij.daos.schedules.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp
    @Column(nullable = false)
    private Instant createdAt;

    private String userId;

    @Column(nullable = false)
    private String url;

    protected ScheduledUrl() {
    }

    public ScheduledUrl(String id, String url, String userId) {
        this.id = id;
        this.url = url;
        this.userId = userId;
    }
}
