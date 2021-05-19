CREATE TABLE IF NOT EXISTS product_category
(
    product_category_id int                NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                varchar(32) UNIQUE NOT NULL,
    description         varchar(128)
);

CREATE TABLE IF NOT EXISTS supplier
(
    supplier_id int                NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        varchar(32) UNIQUE NOT NULL
);

CREATE unique INDEX UX_SUPPLIER_NAME ON supplier (name);

CREATE TABLE IF NOT EXISTS product
(
    product_id          int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                varchar(32)  NOT NULL,
    description         varchar(128) NOT NULL,
    price               decimal      NOT NULL,
    weight              double,
    image_url           varchar(MAX),
    product_category_id int          NOT NULL,
    supplier_id         int          NOT NULL,
    CONSTRAINT FK_PRODUCT_PRODUCT_CATEGORY FOREIGN KEY (product_category_id) references product_category (product_category_id),
    CONSTRAINT FK_PRODUCT_SUPPLIER FOREIGN KEY (supplier_id) references supplier (supplier_id)
);

CREATE TABLE IF NOT EXISTS address
(
    address_id     int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    country        varchar(32)  NOT NULL,
    city           varchar(32)  NOT NULL,
    county         varchar(32)  NOT NULL,
    street_address varchar(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS location
(
    location_id int         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        varchar(32) NOT NULL,
    address_id  int         NOT NULL,
    CONSTRAINT FK_LOCATION_ADDRESS FOREIGN KEY (address_id) references address (address_id)
);

CREATE TABLE IF NOT EXISTS customer
(
    customer_id int                NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name  varchar(64)        NOT NULL,
    last_name   varchar(32)        NOT NULL,
    username    varchar(32) UNIQUE NOT NULL,
    password    varchar(32)        NOT NULL,
    email       varchar(32) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS stock
(
    stock_id    int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_id  int NOT NULL,
    location_id int NOT NULL,
    quantity    int NOT NULL,
    CONSTRAINT FK_STOCK_LOCATION FOREIGN KEY (location_id) references location (location_id),
    CONSTRAINT FK_STOCK_PRODUCT FOREIGN KEY (product_id) references product (product_id)
);

ALTER TABLE stock
    ADD CONSTRAINT UQ_PRODUCT_LOCATION UNIQUE (product_id, location_id);

CREATE TABLE IF NOT EXISTS purchase
(
    purchase_id int       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    customer_id int       NOT NULL,
    address_id  int       NOT NULL,
    created_at  timestamp NOT NULL,
    CONSTRAINT FK_PURCHASE_CUSTOMER FOREIGN KEY (customer_id) references customer (customer_id),
    CONSTRAINT FK_PURCHASE_ADDRESS FOREIGN KEY (address_id) references address (address_id)
);

CREATE TABLE IF NOT EXISTS revenue
(
    revenue_id  int       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    location_id int       NOT NULL,
    date        timestamp NOT NULL,
    sum         decimal   NOT NULL,
    CONSTRAINT FK_REVENUE_LOCATION FOREIGN KEY (location_id) references location (location_id)
);

CREATE TABLE IF NOT EXISTS purchase_detail
(
    purchase_detail_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    purchase_id        int NOT NULL,
    shipped_from_id    int NOT NULL,
    product_id         int NOT NULL,
    quantity           int NOT NULL,
    CONSTRAINT FK_PURCHASE_LOCATION FOREIGN KEY (shipped_from_id) references location (location_id),
    CONSTRAINT FK_PURCHASE_DETAILS_PRODUCT FOREIGN KEY (product_id) references product (product_id),
    CONSTRAINT FK_PURCHASE_DETAILS_PURCHASE FOREIGN KEY (purchase_id) references purchase (purchase_id)
);

ALTER TABLE purchase_detail
    ADD CONSTRAINT UQ_PURCHASE_PRODUCT UNIQUE (purchase_id, product_id);

