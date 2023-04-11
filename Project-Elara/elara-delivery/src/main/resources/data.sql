DROP TABLE IF EXISTS addresses CASCADE;

CREATE TABLE addresses
(
    id               VARCHAR(36) NOT NULL,
    postal_code      VARCHAR(64) NOT NULL,
    city             VARCHAR(64) NOT NULL,
    country          VARCHAR(64) NOT NULL,
    street           VARCHAR(64) NOT NULL,
    building_number  VARCHAR(32) NOT NULL,
    apartment_number VARCHAR(32) NOT NULL,
    entrance_number  VARCHAR(32) NOT NULL,
    CONSTRAINT pk_addresses PRIMARY KEY (id)
);

CREATE TABLE user_delivery_addresses
(
    id                 VARCHAR(36) NOT NULL,
    userid             VARCHAR(36) NOT NULL,
    deleted            BOOLEAN DEFAULT FALSE,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_user_delivery_addresses PRIMARY KEY (id)
);

CREATE TABLE user_delivery_addresses_addresses
(
    addresses_id                VARCHAR(36) NOT NULL,
    saved_delivery_addresses_id VARCHAR(36) NOT NULL,
    CONSTRAINT pk_user_delivery_addresses_addresses PRIMARY KEY (addresses_id, saved_delivery_addresses_id)
);

ALTER TABLE user_delivery_addresses_addresses
    ADD CONSTRAINT fk_usedeladdadd_on_address FOREIGN KEY (addresses_id) REFERENCES addresses (id);

ALTER TABLE user_delivery_addresses_addresses
    ADD CONSTRAINT fk_usedeladdadd_on_saved_delivery_addresses FOREIGN KEY (saved_delivery_addresses_id) REFERENCES user_delivery_addresses (id);