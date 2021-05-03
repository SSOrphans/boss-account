INSERT INTO user VALUES (
                         1, 1, 1,
                         'testuser',
                         'test@email.com',
                         -- 'password'
                         '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8',
                         '1620026671', null, true, false
                        );

INSERT INTO account_type (id, name) VALUES (1, 'ACCOUNT_CHECKING');
INSERT INTO account_type (id, name) VALUES (2, 'ACCOUNT_SAVING');
INSERT INTO account_type (id, name) VALUES (3, 'ACCOUNT_CREDIT');

INSERT INTO account (type_id, branch_id, name, balance, opened, closed, confirmed, active)
VALUES (
           2, 1, 'Dummy Saving A', 12.36, '2019-08-19 13:23:32', '2020-09-03 10:44:56', true, false
       );
INSERT INTO account (type_id, branch_id, name, balance, opened, closed, confirmed, active)
VALUES (
        1, 1, 'Dummy Checking A', 9999.99, '2020-12-19 07:38:55', null, true, true
       );
INSERT INTO account (type_id, branch_id, name, balance, opened, closed, confirmed, active)
VALUES (
           2, 1, 'Dummy Saving A', 7777.77, '2021-01-19 1:38:55', null, true, true
       );
INSERT INTO account (type_id, branch_id, name, balance, opened, closed, confirmed, active)
VALUES (
           1, 1, 'Dummy Checking B', 500.00, '2021-04-19 09:52:53', null, true, false
       );

INSERT INTO account_users (account_id, user_id) VALUES (1,1);
INSERT INTO account_users (account_id, user_id) VALUES (2,1);
INSERT INTO account_users (account_id, user_id) VALUES (3,1);
INSERT INTO account_users (account_id, user_id) VALUES (4,1);
