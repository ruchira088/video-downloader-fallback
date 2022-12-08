package com.ruchij.daos.user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruchij.daos.authorization.models.Role;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Setter
@Getter
@Entity(name = "api_user")
public class User implements Serializable {
    @Id
    private String id;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @JsonIgnore
    @Column(nullable = false)
    private Instant updatedAt;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private List<Role> roles;

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
