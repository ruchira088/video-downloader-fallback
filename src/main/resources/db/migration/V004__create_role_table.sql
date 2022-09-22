CREATE TABLE role
(
    id         VARCHAR(36),
    created_at TIMESTAMP   NOT NULL,
    user_id    VARCHAR(36) NOT NULL,
    role_type  VARCHAR(15) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_role_user_id FOREIGN KEY (user_id) REFERENCES api_user (id)
);