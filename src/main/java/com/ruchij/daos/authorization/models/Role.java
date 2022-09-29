package com.ruchij.daos.authorization.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@Entity
public class Role implements Serializable {
    @Id
    private String id;

    @JsonIgnore
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @JsonIgnore
    @Column(nullable = false, updatable = false)
    private String userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    protected Role() {
    }

    public Role(String id, Instant createdAt, String userId, RoleType roleType) {
        this.id = id;
        this.createdAt = createdAt;
        this.userId = userId;
        this.roleType = roleType;
    }
}
