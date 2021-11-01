CREATE SCHEMA IF NOT EXISTS boss;

CREATE TABLE IF NOT EXISTS boss.confirmation
(
    id          BIGINT   NOT NULL AUTO_INCREMENT,
    type        TINYINT  NOT NULL,
    confirmable BIGINT   NOT NULL,
    hash        CHAR(36) NOT NULL,
    until       BIGINT   NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT uq_confirmation_id UNIQUE (id),
    CONSTRAINT uq_confirmation_hash UNIQUE (hash)
);

CREATE TABLE IF NOT EXISTS boss.user
(
    -- Base holder information
    id                 BIGINT        NOT NULL AUTO_INCREMENT,
    type               TINYINT       NOT NULL,
    branch             BIGINT        NOT NULL,

    -- Embedded UserProfile
    username           NVARCHAR(16)  NOT NULL,
    email              NVARCHAR(128) NOT NULL,
    password           VARCHAR(64)   NOT NULL, -- HASHED
    created            BIGINT        NOT NULL,
    deleted            BIGINT        NULL,
    confirmed          BIT           NOT NULL,
    enabled            BIT           NOT NULL,
    locked             BIT           NOT NULL,

    -- Embedded UserProfile -> UserSettings
    transaction_alerts INT           NULL,
    balance_alerts     INT           NULL,

    -- Embedded UserInfo
    full_name          NVARCHAR(255) NULL,
    identification     CHAR(64)      NULL,     -- National Identification code, HASHED.
    dob                DATE          NULL,

    -- Embedded UserInfo -> UserAddress
    -- May need to be separated into table if multiple can exist.
    country            CHAR(2)       NULL,     -- UK, US, CA, etc.
    postal             NVARCHAR(10)  NULL,     -- Postal/Zip code
    region             NVARCHAR(64)  NULL,     -- State, Province, Region
    city               NVARCHAR(64)  NULL,
    street             NVARCHAR(64)  NULL,
    premise            NVARCHAR(64)  NULL,     -- Apartment, Suite, Box number, etc.

    -- Embedded UserInfo -> UserCellular
    -- May need to be separated into table if multiple can exist.
    presentation       SMALLINT      NULL,
    number             CHAR(15)      NULL,

    PRIMARY KEY (id),
    CONSTRAINT uq_user_id UNIQUE (id),
    CONSTRAINT uq_user_username UNIQUE (username),
    CONSTRAINT uq_user_email UNIQUE (email),
    CONSTRAINT uq_user_identification UNIQUE (identification)
);

CREATE TABLE IF NOT EXISTS boss.account
(
    id              BIGINT       NOT NULL,
    type            TINYINT      NOT NULL,
    branch          BIGINT       NOT NULL, -- The branch the account belongs to.

    -- Embedded AccountInfo
    name            NVARCHAR(32) NULL,
    balance         FLOAT        NOT NULL,
    pending_balance FLOAT        NOT NULL,
    opened          TIMESTAMP    NOT NULL,
    closed          TIMESTAMP    NULL,
    confirmed       BIT          NOT NULL,
    enabled         BIT          NOT NULL,
    locked          BIT          NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS boss.transaction
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    type        TINYINT      NOT NULL,
    account     BIGINT       NOT NULL,
    overdraft   BIGINT       NULL, -- not currently implemented
    atm         BIGINT       NULL, -- not currently implemented

    -- Embedded TransactionInfo
    merchant    NVARCHAR(32) NOT NULL,
    amount      FLOAT        NOT NULL,
    new_balance FLOAT        NOT NULL,
    date        TIMESTAMP    NOT NULL,
    flags       TINYINT      NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT uq_transaction_id UNIQUE (id),
    CONSTRAINT fk_transaction_account
        FOREIGN KEY (account) REFERENCES boss.account (id)
--    CONSTRAINT fk_transaction_overdraft
--        FOREIGN KEY (overdraft) REFERENCES boss.overdraft (id),
--    CONSTRAINT fk_transaction_atm
--        FOREIGN KEY (atm) REFERENCES boss.atm (id)
);

CREATE TABLE IF NOT EXISTS boss.loan
(
    id       BIGINT      NOT NULL AUTO_INCREMENT,
    type     TINYINT     NOT NULL,
    number   VARCHAR(64) NOT NULL,
    user     BIGINT      NOT NULL,
    account  BIGINT      NOT NULL,

    -- Embedded LoanInfo
    total    FLOAT       NOT NULL,
    interest FLOAT       NOT NULL,
    taken_at TIMESTAMP   NOT NULL,
    due_by   DATE        NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT uq_loan_id UNIQUE (id),
    CONSTRAINT uq_loan_number UNIQUE (number),
    CONSTRAINT uq_loan_account UNIQUE (account),
    CONSTRAINT fk_loan_user
        FOREIGN KEY (user) REFERENCES boss.user (id),
    CONSTRAINT fk_loan_account
        FOREIGN KEY (account) REFERENCES boss.account (id)
);

CREATE TABLE IF NOT EXISTS boss.user_accounts
(
    user    BIGINT NOT NULL,
    account BIGINT NOT NULL,

    CONSTRAINT fk_user_accounts_user
        FOREIGN KEY (user) REFERENCES boss.user (id),
    CONSTRAINT fk_user_accounts_account
        FOREIGN KEY (account) REFERENCES boss.account (id)
);

CREATE TABLE IF NOT EXISTS boss.rewards_program
(
    id          INT         NOT NULL AUTO_INCREMENT,
    type        TINYINT     NOT NULL,
    name        VARCHAR(16) NOT NULL,
    distributor VARCHAR(16) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT uq_rewards_program_id UNIQUE (id)
);

CREATE TABLE IF NOT EXISTS boss.card_level
(
    id          SMALLINT    NOT NULL AUTO_INCREMENT,
    name        VARCHAR(16) NOT NULL,
    distributor VARCHAR(16) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT uq_card_level_id UNIQUE (id)
);

CREATE TABLE IF NOT EXISTS boss.card
(
    -- Base cardEntity information.
    id      BIGINT   NOT NULL AUTO_INCREMENT,
    type    TINYINT  NOT NULL,
    account BIGINT   NULL,     -- Backing account ID.
    holder  BIGINT   NULL,     -- Backing account holder ID.

    -- Embedded PhysicalCardInfo
    number  CHAR(19) NOT NULL, -- UNHASHED
    cvv     CHAR(64) NOT NULL, -- HASHED
    created BIGINT   NOT NULL,
    expiry  BIGINT   NOT NULL,

    -- Embedded LogicalCardInfo
    pin     CHAR(64) NULL,     -- HASHED
    since   BIGINT   NULL,     -- Last active, since
    flags   TINYINT  NOT NULL, -- Card flags: confirmed, active, stolen, etc.

    PRIMARY KEY (id),
    CONSTRAINT uq_card_id UNIQUE (id),
    CONSTRAINT uq_card_number UNIQUE (number),
    CONSTRAINT fk_card_account
        FOREIGN KEY (account) REFERENCES boss.account (id),
    CONSTRAINT fk_card_holder
        FOREIGN KEY (holder) REFERENCES boss.user (id)
);

CREATE TABLE IF NOT EXISTS boss.debit_card
(
    backing BIGINT NOT NULL,
    -- No other information to store here currently.

    PRIMARY KEY (backing),
    CONSTRAINT uq_debit_card_backing UNIQUE (backing)
);

CREATE TABLE IF NOT EXISTS boss.credit_card
(
    backing BIGINT   NOT NULL, -- Card backing ID.
    level   SMALLINT NOT NULL, -- The type of credit cardEntity this is.
    apr     FLOAT    NOT NULL, -- Annual percentage rate
    ceiling FLOAT    NOT NULL, -- The maximum allowance on the cardEntity.

    -- APR / 12 gives monthly interest rate on outstanding balance.

    PRIMARY KEY (backing),
    CONSTRAINT uq_credit_card_backing UNIQUE (backing),
    CONSTRAINT fk_credit_card_backing
        FOREIGN KEY (backing) REFERENCES boss.card (id),
    CONSTRAINT fk_credit_card_level
        FOREIGN KEY (level) REFERENCES boss.card_level (id)
);

CREATE TABLE IF NOT EXISTS boss.card_rewards_programs
(
    card    BIGINT NOT NULL,
    program BIGINT NOT NULL,
    points  INT    NOT NULL,

    CONSTRAINT fk_card_rewards_programs_card
        FOREIGN KEY (card) REFERENCES boss.credit_card (backing),
    CONSTRAINT fk_card_rewards_programs_program
        FOREIGN KEY (program) REFERENCES boss.rewards_program (id)
);

