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

INSERT INTO boss.account (id, type_id, branch_id, name, balance, opened, closed, confirmed, active)
VALUES (398749873498238, 1, 1, 'Stubbed Saving A', 12.36, '2019-08-19 13:23:32', '2020-09-03 10:44:56', true, false);
INSERT INTO boss.account (id, type_id, branch_id, name, balance, opened, closed, confirmed, active)
VALUES (420391249212312, 2, 1, 'Stubbed Checking A', 9999.99, '2020-12-19 07:38:55', null, true, true);
INSERT INTO boss.account (id, type_id, branch_id, name, balance, opened, closed, confirmed, active)
VALUES (234893491289398, 1, 1, 'Stubbed Saving A', 7777.77, '2021-01-19 1:38:55', null, true, true);
INSERT INTO boss.account (id, type_id, branch_id, name, balance, opened, closed, confirmed, active)
VALUES (985896289370229, 2, 1, 'Stubbed Checking B', 500.00, '2021-04-19 09:52:53', null, true, false);

INSERT INTO boss.account_users (account_id, user_id)
VALUES (398749873498238, 1);
INSERT INTO boss.account_users (account_id, user_id)
VALUES (420391249212312, 1);
INSERT INTO boss.account_users (account_id, user_id)
VALUES (234893491289398, 1);
INSERT INTO boss.account_users (account_id, user_id)
VALUES (985896289370229, 2);

insert into boss.account_holder(user_id, full_name, dob, ssn, address, city, state, zip, phone)
values(1, 'Trevor Philips', 20130917, '123-45-6789', '16703 Nicklaus Dr', 'Los Angeles', 'CA', 91342, '+12735550136');
insert into boss.account_holder(user_id, full_name, dob, ssn, address, city, state, zip, phone)
values(2, 'Aurora Tea', 19930721, '987-654-3210', '1345 Ocean St', 'San Francisco', 'CA', 94512, '+14105682345');

