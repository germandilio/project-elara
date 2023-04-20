DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS user_service_info CASCADE;
DROP TABLE IF EXISTS user_profiles CASCADE;

CREATE TABLE roles
(
    id               VARCHAR(36) NOT NULL,
    role             VARCHAR(64) NOT NULL,
    role_description VARCHAR(1024),
    deleted          BOOLEAN DEFAULT FALSE,
    creation_time    TIMESTAMP WITHOUT TIME ZONE,
    last_update_time TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

ALTER TABLE roles
    ADD CONSTRAINT uc_roles_role UNIQUE (role);

CREATE TABLE user_profiles
(
    id               VARCHAR(36)  NOT NULL,
    email            VARCHAR(255) NOT NULL,
    first_name       VARCHAR(255) NOT NULL,
    last_name        VARCHAR(255) NOT NULL,
    picture_url      VARCHAR(512),
    birth_date       date,
    deleted          BOOLEAN DEFAULT FALSE,
    creation_time    TIMESTAMP WITHOUT TIME ZONE,
    last_update_time TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_user_profiles PRIMARY KEY (id)
);

ALTER TABLE user_profiles
    ADD CONSTRAINT uc_user_profiles_email UNIQUE (email);

CREATE TABLE user_service_info
(
    id                              VARCHAR(36)  NOT NULL,
    login                           VARCHAR(255) NOT NULL,
    password                        VARCHAR(80)  NOT NULL,
    email_verification_token        VARCHAR(128),
    password_reset_token            VARCHAR(128),
    password_reset_token_expired_at TIMESTAMP WITHOUT TIME ZONE,
    role_id                         VARCHAR(36),
    deleted                         BOOLEAN DEFAULT FALSE,
    creation_time                   TIMESTAMP WITHOUT TIME ZONE,
    last_update_time                TIMESTAMP WITHOUT TIME ZONE,
    version                         INTEGER,
    user_profile_id                 VARCHAR(36),
    CONSTRAINT pk_user_service_info PRIMARY KEY (id)
);

CREATE UNIQUE INDEX idx_userserviceinfo_login_unq ON user_service_info (login);

ALTER TABLE user_service_info
    ADD CONSTRAINT uc_user_service_info_emailverificationtoken UNIQUE (email_verification_token);

ALTER TABLE user_service_info
    ADD CONSTRAINT uc_user_service_info_login UNIQUE (login);

ALTER TABLE user_service_info
    ADD CONSTRAINT uc_user_service_info_passwordresettoken UNIQUE (password_reset_token);

ALTER TABLE user_service_info
    ADD CONSTRAINT FK_USER_SERVICE_INFO_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE user_service_info
    ADD CONSTRAINT FK_USER_SERVICE_INFO_ON_USERPROFILE FOREIGN KEY (user_profile_id) REFERENCES user_profiles (id);

INSERT INTO roles (id, role, role_description, creation_time, last_update_time)
VALUES ('aec35e77-4c97-4807-853f-7de96617c00b', 'EMAIL_NOT_VERIFIED', 'Initial role, no access to orders', NOW(),
        NOW());
INSERT INTO roles (id, role, role_description, creation_time, last_update_time)
VALUES ('f82c4d8a-c4c4-4122-9a3a-72b7251c08f1', 'USER', 'User access', NOW(), NOW());

INSERT INTO roles (id, role, role_description, creation_time, last_update_time)
VALUES ('f52318ca-8734-442e-b0df-a26693c2ba2f', 'ADMIN', 'Admin access', NOW(), NOW());

-- add admin
INSERT INTO user_profiles (id, email, first_name, last_name, creation_time, last_update_time)
values ('b8e66310-4a80-4dd8-8692-55bbca419e4b', 'admin@gmail.com', 'Admin', 'Admin', NOW(), NOW());
INSERT INTO user_service_info (id, login, password, role_id, creation_time, last_update_time, version, user_profile_id)
values ('b8e66310-4a80-4dd8-8692-55bbca419e4b', 'admin@gmail.com', 'password123456', 'f52318ca-8734-442e-b0df-a26693c2ba2f', NOW(), NOW(), 1, 'b8e66310-4a80-4dd8-8692-55bbca419e4b');