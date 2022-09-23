CREATE TABLE credentials
(
    user_id         VARCHAR(36),
    updated_at      TIMESTAMP,
    hashed_password VARCHAR(127) NOT NULL,

    PRIMARY KEY (user_id),
    CONSTRAINT fk_credentials_user_id FOREIGN KEY (user_id) REFERENCES api_user (id)
);