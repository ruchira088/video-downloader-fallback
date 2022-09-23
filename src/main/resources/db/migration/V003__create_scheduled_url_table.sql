CREATE TABLE scheduled_url
(
    id         VARCHAR(36),
    status     VARCHAR(15)   NOT NULL,
    created_at TIMESTAMP     NOT NULL,
    updated_at TIMESTAMP     NOT NULL,
    user_id    VARCHAR(36)   NOT NULL,
    url        VARCHAR(2047) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_scheduled_url_user_id FOREIGN KEY (user_id) REFERENCES api_user (id),
    CONSTRAINT uc_user_id_url UNIQUE (user_id, url)
);