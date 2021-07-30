create schema if not exists boss;
use boss;
CREATE TABLE IF NOT EXISTS account
(
    id         LONG              NOT NULL,
    type_id    TINYINT UNSIGNED NOT NULL,
    branch_id  INT UNSIGNED     NOT NULL,
    name       VARCHAR(32)      NULL,
    balance    FLOAT UNSIGNED   NOT NULL,
    opened     DATETIME         NOT NULL,
    closed     DATETIME         NULL,
    confirmed  BIT              NOT NULL,
    active     BIT              NOT NULL,
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
    account_id LONG      NOT NULL,
    user_id              INT UNSIGNED NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);

create table if not exists account_holder
(
    user_id		INT UNSIGNED	NOT NULL,
    full_name	VARCHAR(64)		NOT NULL,
    dob			DATE			NOT NULL,
    ssn			CHAR(64)		NOT NULL,
    address		CHAR(255)		NOT NULL,
    city		CHAR(64) 		NOT NULL,
    state		CHAR(32)		NOT NULL,
    zip			INT				NOT NULL,
    phone		CHAR(16)		NOT NULL,

    PRIMARY KEY	(user_id),
    FOREIGN KEY	(user_id) REFERENCES boss.user (id)
    );