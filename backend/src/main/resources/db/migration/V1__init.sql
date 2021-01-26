CREATE TABLE auth
(
    passkey VARCHAR(45) NOT NULL UNIQUE,
    user_id VARCHAR(45) NOT NULL UNIQUE,
    email   VARCHAR(45) NOT NULL PRIMARY KEY,
    refresh_token VARCHAR(64) NOT NULL
);

CREATE TABLE user
(
    user_id VARCHAR(45) PRIMARY KEY,
    name    VARCHAR(90) NOT NULL
);

INSERT INTO user (user_id, name)
VALUES ('5435435', 'Nikhil Nayak');
INSERT INTO user (user_id, name)
VALUES ('7566736', 'Mycroft Holmes');

CREATE TABLE expense
(
    expense_id VARCHAR(45) PRIMARY KEY,
    user_id    VARCHAR(45) NOT NULL
);


CREATE TABLE expense_entity
(
    expenseEntity_id VARCHAR(45) PRIMARY KEY,
    expense_id       VARCHAR(45)                                  NOT NULL,
    expense_type     ENUM ('utilities', 'technology', 'everyday') NOT NULL,
    description      VARCHAR(200)                                 NULL,
    value            DECIMAL(10, 2)                               NOT NULL NOT NULL
);

CREATE TABLE income
(
    income_id varchar(45) PRIMARY KEY,
    user_id   varchar(45) NOT NULL
);


CREATE TABLE income_entity
(
    income_entity_id VARCHAR(45) PRIMARY KEY,
    income_id        VARCHAR(45)                                  NOT NULL,
    income_type      ENUM ('utilities', 'technology', 'everyday') NOT NULL,
    description      VARCHAR(200)                                 NULL,
    value            DECIMAL(10, 2)                               NOT NULL
);
