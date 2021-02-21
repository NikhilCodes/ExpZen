CREATE TABLE auth
(
    passkey       VARCHAR(60) NOT NULL UNIQUE,
    user_id       VARCHAR(36) NOT NULL UNIQUE,
    email         VARCHAR(45) NOT NULL PRIMARY KEY,
    refresh_token VARCHAR(36) NOT NULL
);

CREATE TABLE user
(
    user_id VARCHAR(36) PRIMARY KEY,
    name    VARCHAR(90) NOT NULL,
    role    VARCHAR(5)  NOT NULL,
    enabled INT DEFAULT 1
);

CREATE TABLE expense
(
    expense_id   VARCHAR(36) PRIMARY KEY,
    user_id      VARCHAR(36)    NOT NULL,
    expense_type ENUM('TECH', 'EVERYDAY', 'FOOD', 'UTILITIES') NOT NULL,
    description  VARCHAR(200) NULL,
    value        DECIMAL(10, 2) NOT NULL NOT NULL,
    created_on   DATE
);

insert into expense(expense_id, user_id, expense_type, description, value, created_on)
VALUES ('32323234234234234', '648f3d9a-39e8-4b1f-912c-da1c71bf718d', 'UTILITIES', 'Bought a lamp', 442.5, '2021-2-10');

insert into expense(expense_id, user_id, expense_type, description, value, created_on)
VALUES ('32343545623423234', '648f3d9a-39e8-4b1f-912c-da1c71bf718d', 'TECH', 'Raspberry Pi 4 8GB', 7292.2, '2021-2-14');

insert into expense(expense_id, user_id, expense_type, description, value, created_on)
VALUES ("32323005677567234", "648f3d9a-39e8-4b1f-912c-da1c71bf718d", "EVERYDAY", "Tea", 120, "2021-2-17");

CREATE TABLE income
(
    income_id   varchar(36) PRIMARY KEY,
    user_id     varchar(36)    NOT NULL,
    income_type ENUM ('utilities', 'technology', 'everyday') NOT NULL,
    description VARCHAR(200) NULL,
    value       DECIMAL(10, 2) NOT NULL
);
