CREATE TABLE api_user
(
    id         VARCHAR(36),
    created_at TIMESTAMP           NOT NULL,
    updated_at TIMESTAMP,
    email      VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(63)         NOT NULL,
    last_name  VARCHAR(63),

    PRIMARY KEY (id)
);