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
    expense_id VARCHAR(36) PRIMARY KEY,
    user_id    VARCHAR(36) NOT NULL
);


CREATE TABLE expense_entity
(
    expenseEntity_id VARCHAR(36) PRIMARY KEY,
    expense_id       VARCHAR(36)    NOT NULL,
    expense_type     ENUM ('utilities', 'technology', 'everyday') NOT NULL,
    description      VARCHAR(200) NULL,
    value            DECIMAL(10, 2) NOT NULL NOT NULL
);

CREATE TABLE income
(
    income_id varchar(36) PRIMARY KEY,
    user_id   varchar(36) NOT NULL
);


CREATE TABLE income_entity
(
    income_entity_id VARCHAR(36) PRIMARY KEY,
    income_id        VARCHAR(36)    NOT NULL,
    income_type      ENUM ('utilities', 'technology', 'everyday') NOT NULL,
    description      VARCHAR(200) NULL,
    value            DECIMAL(10, 2) NOT NULL
);
