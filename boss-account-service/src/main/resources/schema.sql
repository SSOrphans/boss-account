CREATE TABLE IF NOT EXISTS account
(
    id        INT UNSIGNED     NOT NULL AUTO_INCREMENT,
    type_id   TINYINT UNSIGNED NOT NULL,
    branch_id INT UNSIGNED     NOT NULL,
    name      VARCHAR(32)      NULL,
    balance   FLOAT UNSIGNED   NOT NULL,
    opened    DATETIME         NOT NULL,
    closed    DATETIME         NULL,
    confirmed BIT              NOT NULL,
    active    BIT              NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS account_type
(
    id   TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name CHAR(16)         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user
(
    id        INT UNSIGNED     NOT NULL AUTO_INCREMENT,
    type_id   TINYINT UNSIGNED NOT NULL,
    branch_id INT UNSIGNED     NOT NULL,
    username  VARCHAR(16)      NOT NULL,
    email     VARCHAR(128)     NOT NULL,
    password  VARCHAR(64)      NOT NULL,
    created   BIGINT           NOT NULL,
    deleted   BIGINT           NULL,
    enabled   BIT              NOT NULL,
    locked    BIT              NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS account_users
(
    account_id INT UNSIGNED NOT NULL,
    user_id    INT UNSIGNED NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (id),
    FOREIGN KEY (user_id)REFERENCES user (id)
);