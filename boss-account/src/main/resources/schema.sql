create schema if not exists boss;
CREATE TABLE IF NOT EXISTS boss.account
(
    id              LONG             NOT NULL,
    type_id         TINYINT UNSIGNED NOT NULL,
    branch_id       INT UNSIGNED     NOT NULL,
    name            VARCHAR(32)      NULL,
    balance         FLOAT UNSIGNED   NOT NULL,
    pending_balance FLOAT UNSIGNED   NOT NULL,
    opened          DATETIME         NOT NULL,
    closed          DATETIME         NULL,
    confirmed       BIT              NOT NULL,
    active          BIT              NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS boss.account_type
(
    id   TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name CHAR(16)         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS boss.user
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

CREATE TABLE IF NOT EXISTS boss.account_users
(
    account_id LONG         NOT NULL,
    user_id    INT UNSIGNED NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS boss.transaction_type (
            id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
            name CHAR(36) NOT NULL
    );

CREATE TABLE IF NOT EXISTS boss.transaction (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    type_id TINYINT UNSIGNED NOT NULL,
    account_id LONG NOT NULL,
    overdraft_id INT UNSIGNED NULL,
    atm_transaction_id INT UNSIGNED NULL,
    merchant_name VARCHAR(64) NOT NULL,
    amount FLOAT NOT NULL,
    new_balance FLOAT NOT NULL,
    cashback FLOAT NOT NULL,
    date TIMESTAMP NOT NULL,
    succeeded BIT NOT NULL,
    pending BIT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (type_id) REFERENCES transaction_type (id)
);

CREATE TABLE IF NOT EXISTS boss.rewards_program
(
    id              INT UNSIGNED AUTO_INCREMENT    NOT NULL,
    type            TINYINT UNSIGNED NOT NULL,
    name            VARCHAR(32)      NOT NULL,
    organization    VARCHAR(16)      NOT NULL,
    cashback_rate   FLOAT            NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS boss.reward_type (
    id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name CHAR(36) NOT NULL
);