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
    expense_id         VARCHAR(36) PRIMARY KEY,
    user_id            VARCHAR(36)                                    NOT NULL,
    expense_type       ENUM ('TECH', 'EVERYDAY', 'FOOD', 'UTILITIES') NOT NULL,
    description        VARCHAR(200)                                   NULL,
    value              DECIMAL(10, 2)                                 NOT NULL NOT NULL,
    created_on         DATE,
    creation_timestamp TIMESTAMP
);

CREATE TABLE income
(
    income_id          VARCHAR(36) PRIMARY KEY,
    user_id            VARCHAR(36)                                      NOT NULL,
    income_type        ENUM ('PAYCHECK', 'REFUND', 'INTEREST', 'BONUS') NOT NULL,
    value              DECIMAL(10, 2)                                   NOT NULL,
    created_on         DATE,
    creation_timestamp TIMESTAMP
);

CREATE TABLE due
(
    due_id             VARCHAR(36) PRIMARY KEY,
    user_id            VARCHAR(36)    NOT NULL,
    description        VARCHAR(100)   NOT NULL,
    value              DECIMAL(10, 2) NOT NULL,
    created_on         DATE,
    creation_timestamp TIMESTAMP
);