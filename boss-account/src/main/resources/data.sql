INSERT INTO boss.user
VALUES (1, 1, 1,
        'testuser',
        'test@email.com',
           -- 'password'
        '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8',
        '1620026671', null, true, false);

INSERT INTO boss.user
VALUES (2, 2, 1,
        'demouser',
        'demo@email.com',
           -- 'password'
        '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8',
        '1620026671', null, true, false);

INSERT INTO boss.account_type (id, name)
VALUES (1, 'ACCOUNT_SAVING');
INSERT INTO boss.account_type (id, name)
VALUES (2, 'ACCOUNT_CHECKING');
INSERT INTO boss.account_type (id, name)
VALUES (3, 'ACCOUNT_CREDIT');

INSERT INTO boss.account (id, type_id, branch_id, name, balance, pending_balance,opened, closed, confirmed, active)
VALUES (398749873498238, 1, 1, 'Stubbed Saving A', 12.36, 0,'2019-08-19 13:23:32', '2020-09-03 10:44:56', true, false);
INSERT INTO boss.account (id, type_id, branch_id, name, balance, pending_balance,opened, closed, confirmed, active)
VALUES (420391249212312, 2, 1, 'Stubbed Checking A', 9999.99, 0,'2020-12-19 07:38:55', null, true, true);
INSERT INTO boss.account (id, type_id, branch_id, name, balance, pending_balance,opened, closed, confirmed, active)
VALUES (234893491289398, 1, 1, 'Stubbed Saving A', 7777.77, 0,'2021-01-19 1:38:55', null, true, true);
INSERT INTO boss.account (id, type_id, branch_id, name, balance, pending_balance,opened, closed, confirmed, active)
VALUES (985896289370229, 2, 1, 'Stubbed Checking B', 500.00, 0,'2021-04-19 09:52:53', null, true, false);

INSERT INTO boss.account_users (account_id, user_id)
VALUES (398749873498238, 1);
INSERT INTO boss.account_users (account_id, user_id)
VALUES (420391249212312, 1);
INSERT INTO boss.account_users (account_id, user_id)
VALUES (234893491289398, 1);
INSERT INTO boss.account_users (account_id, user_id)
VALUES (985896289370229, 1);

INSERT INTO boss.transaction_type (id, name)
VALUES (1, 'TRANSACTION_DEPOSIT');
INSERT INTO boss.transaction_type (id, name)
VALUES (2, 'TRANSACTION_WITHDRAW');
INSERT INTO boss.transaction_type (id, name)
VALUES (3, 'TRANSACTION_TRANSFER');
INSERT INTO boss.transaction_type (id, name)
VALUES (4, 'TRANSACTION_PAYMENT');
INSERT INTO boss.transaction_type (id, name)
VALUES (5, 'TRANSACTION_CHECK');
INSERT INTO boss.transaction_type (id, name)
VALUES (6, 'TRANSACTION_ATM_DEPOSIT');
INSERT INTO boss.transaction_type (id, name)
VALUES (7, 'TRANSACTION_ATM_WITHDRAW');

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (2, 1, null, 2, 'TestMerchant', 12.34, 123.45, 0,NOW(), true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                              cashback,
                         date,
                         succeeded,
                         pending)
values (398749873498238, 1, null, 1, 'Amazon Market', 38.32, 500.00, 0,'2021-01-22 16:07:54', true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (398749873498238, 4, null, 1, 'Panda Express', 6.15, 493.85, 0,'2021-01-26 0:30:12', true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (420391249212312, 2, null, 1, 'Google Events', 7.01, 486.84, 0,'2021-01-28 16:43:51', true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (398749873498238, 4, null, 1, 'Uber', 31.35, 455.49, 0,'2021-01-30 20:37:40', true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (398749873498238, 4, null, 1, 'Smoothstack', 44.30, 411.19, 0,'2021-02-07 5:29:52', true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (420391249212312, 4, null, 1, 'Slack Inc.', 41.57, 369.62, 0,'2021-02-08 20:33:12', true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (398749873498238, 4, null, 1, 'Smoothstack', 27.75, 341.87, 0,'2021-02-08 22:06:58', true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (398749873498238, 4, null, 1, 'Uber', 8.84, 333.03, 0,'2021-02-16 23:50:47', true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (420391249212312, 2, null, 1, 'Google Maps', 47.27, 285.76, 0,'2021-02-21 8:40:23', true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (398749873498238, 4, null, 1, 'Panda Express', 15.13, 270.63, 0,'2021-03-08 14:10:25', true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (398749873498238, 2, null, 1, 'Panda Express', 34.09, 236.54, 0,'2021-03-10 8:39:44', true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (420391249212312, 4, null, 1, 'Uber', 14.47, 222.07, 0,'2021-03-30 23:00:27', true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (398749873498238, 2, null, 1, 'Google Play', 8.82, 213.25, 0,'2021-04-02 8:54:42', true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (398749873498238, 4, null, 1, 'TestMerchant', 8.65, 204.60, 0,'2021-04-05 1:42:51', true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (420391249212312, 2, null, 1, 'Google Services', 48.19, 156.41, 0,'2021-04-06 9:46:06', true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (398749873498238, 4, null, 1, 'Smoothstack', 36.65, 119.76, 0,'2021-04-08 10:03:40', true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (398749873498238, 2, null, 1, 'Google Cloud', 12.17, 107.59, 0,'2021-04-10 15:40:35', true, true);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (420391249212312, 2, null, 1, 'Amazon Market', 24.84, 82.75, 0,'2021-04-13 2:48:23', true, false);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (398749873498238, 4, null, 1, 'Smoothstack', 29.29, 53.46, 0,'2021-04-14 15:56:48', true, true);

insert into boss.transaction (account_id,
                         type_id,
                         overdraft_id,
                         atm_transaction_id,
                         merchant_name,
                         amount,
                         new_balance,
                         cashback,
                         date,
                         succeeded,
                         pending)
values (398749873498238, 4, null, 1, 'Amazon Market', 11.06, 42.40, 0,'2021-04-19 1:38:55', true, true);

insert into boss.transaction (account_id,
                              type_id,
                              overdraft_id,
                              atm_transaction_id,
                              merchant_name,
                              amount,
                              new_balance,
                              cashback,
                              date,
                              succeeded,
                              pending)
values (420391249212312, 4, null, 1, 'Bank of Smoothstack', -100, 3, 3,'2021-04-19 1:38:55', true, false);

insert into boss.rewards_program (type,name,organization,cashback_rate) values (1,'Bank of Smoothstack','BOSS',.03);

INSERT INTO boss.reward_type (id, name)
VALUES (1, 'REWARD_CASHBACK');