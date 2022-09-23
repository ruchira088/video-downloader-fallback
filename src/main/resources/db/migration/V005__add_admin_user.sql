INSERT INTO api_user (id, created_at, updated_at, email, first_name, last_name)
    VALUES (
        '2c806b20-b455-4713-8986-263347793ece',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'me@ruchij.com',
        'Ruchira',
        'Jayasekara'
    );

INSERT INTO credentials (user_id, updated_at, hashed_password)
    VALUES ('2c806b20-b455-4713-8986-263347793ece', CURRENT_TIMESTAMP, '${adminHashedPassword}');

INSERT INTO role (id, created_at, user_id, role_type)
    VALUES ('bb77ede5-5740-4776-bdaa-f1c7af783492', CURRENT_TIMESTAMP, '2c806b20-b455-4713-8986-263347793ece', 'ROLE_ADMIN');