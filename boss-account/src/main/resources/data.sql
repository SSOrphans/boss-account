INSERT INTO boss.user
VALUES (1, 1, 1,
        'sora', 'sorakatadzuma@gmail.com',
        '$2y$10$qBhjEkb4g4H0fBrFh..eUuoIESsHIywolW2wq9BSfjfL9LKdq22Sy',
        '1620026671', NULL, true, true, false,
        NULL, NULL,
        NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL);

INSERT INTO boss.user
VALUES (2, 1, 1,
        'testuser',
        'test@email.com',
           -- 'password'
        '$2a$10$R5TsDhGMu5gMrzH5bDoCJ.do2QW54HgeICfj/N5dVY25PID4BXp3K',
        '1620026671', NULL, true, true, false,
        NULL, NULL,
        NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL);

INSERT INTO boss.user
VALUES (3, 2, 1,
        'demouser',
        'demo@email.com',
           -- 'password'
        '$2a$10$R5TsDhGMu5gMrzH5bDoCJ.do2QW54HgeICfj/N5dVY25PID4BXp3K',
        '1620026671', NULL, true, true, false,
        NULL, NULL,
        NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL);

INSERT INTO boss.account
VALUES (398749873498238, 1, 1, 'Stubbed Saving A', 12.36, 12.36, '2019-08-19 13:23:32', '2020-09-03 10:44:56', true, true, false);
INSERT INTO boss.account
VALUES (420391249212312, 2, 1, 'Stubbed Checking A', 9999.99, 9999.99, '2020-12-19 07:38:55', null, true, true, true);
INSERT INTO boss.account
VALUES (234893491289398, 1, 1, 'Stubbed Saving B', 7777.77, 7777.77, '2021-01-19 1:38:55', null, true, true, true);
INSERT INTO boss.account
VALUES (985896289370229, 2, 1, 'Stubbed Checking B', 500.00, 500.00, '2021-04-19 09:52:53', null, true, true, false);

INSERT INTO boss.user_accounts
VALUES (2, 398749873498238);
INSERT INTO boss.user_accounts
VALUES (2, 420391249212312);
INSERT INTO boss.user_accounts
VALUES (2, 234893491289398);
INSERT INTO boss.user_accounts
VALUES (2, 985896289370229);
