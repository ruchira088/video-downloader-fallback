package com.ruchij.daos.user.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@Entity(name = "api_user")
public class User implements Serializable {
    @Id
    private String id;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    protected User() {
    }

    public User(String id, String email, String firstName, String lastName, Instant timestamp) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = timestamp;
        this.updatedAt = timestamp;
    }
}
