package com.ruchij.daos.credentials.models;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.Instant;

@Setter
@Getter
@Entity
public class Credentials {
    @Id
    private String userId;

    @Column(nullable = false)
    private Instant updatedAt;

    @Column(nullable = false)
    private String hashedPassword;

    protected Credentials() {
    }

    public Credentials(String userId, String hashedPassword, Instant timestamp) {
        this.userId = userId;
        this.hashedPassword = hashedPassword;
        this.updatedAt = timestamp;
    }
}
