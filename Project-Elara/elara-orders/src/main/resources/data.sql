DROP TABLE IF EXISTS addresses CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS payment_details CASCADE;
DROP TABLE IF EXISTS shipment_details CASCADE;
DROP TABLE IF EXISTS shipment_methods CASCADE;

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

CREATE TABLE orders
(
    id                  VARCHAR(36)      NOT NULL,
    userid              VARCHAR(36)      NOT NULL,
    shipment_details_id VARCHAR(36),
    payment_details_id  VARCHAR(36),
    total_with_discount DECIMAL          NOT NULL,
    total               DECIMAL          NOT NULL,
    status              INTEGER          NOT NULL,
    total_height        DOUBLE PRECISION NOT NULL,
    total_length        DOUBLE PRECISION NOT NULL,
    total_width         DOUBLE PRECISION NOT NULL,
    total_weight        DOUBLE PRECISION NOT NULL,
    created_date        TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE payment_details
(
    id          VARCHAR(36)  NOT NULL,
    order_id    VARCHAR(36)  NOT NULL,
    status      INTEGER      NOT NULL,
    user_email  VARCHAR(255) NOT NULL,
    update_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_payment_details PRIMARY KEY (id)
);

CREATE TABLE shipment_details
(
    id                 VARCHAR(36) NOT NULL,
    to_address_id      VARCHAR(36),
    from_address_id    VARCHAR(36),
    delivery_cost      DECIMAL     NOT NULL,
    shipment_method_id VARCHAR(36),
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_shipment_details PRIMARY KEY (id)
);

CREATE TABLE shipment_methods
(
    id                 VARCHAR(36)  NOT NULL,
    tariff_code        INTEGER      NOT NULL,
    tariff_name        VARCHAR(64)  NOT NULL,
    tariff_description VARCHAR(512) NOT NULL,
    delivery_mode      INTEGER      NOT NULL,
    delivery_sum       DECIMAL      NOT NULL,
    period_min         INTEGER      NOT NULL,
    period_max         INTEGER      NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_shipment_methods PRIMARY KEY (id)
);

ALTER TABLE shipment_details
    ADD CONSTRAINT FK_SHIPMENT_DETAILS_ON_FROM_ADDRESS FOREIGN KEY (from_address_id) REFERENCES addresses (id);

ALTER TABLE shipment_details
    ADD CONSTRAINT FK_SHIPMENT_DETAILS_ON_SHIPMENT_METHOD FOREIGN KEY (shipment_method_id) REFERENCES shipment_methods (id);

ALTER TABLE shipment_details
    ADD CONSTRAINT FK_SHIPMENT_DETAILS_ON_TO_ADDRESS FOREIGN KEY (to_address_id) REFERENCES addresses (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_PAYMENT_DETAILS FOREIGN KEY (payment_details_id) REFERENCES payment_details (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_SHIPMENT_DETAILS FOREIGN KEY (shipment_details_id) REFERENCES shipment_details (id);