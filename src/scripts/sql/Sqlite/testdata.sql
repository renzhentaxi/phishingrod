INSERT INTO SessionType
(name, host, port, auth, tls) VALUES ('gmail', 'smtp.gmail.com', 587, 1, 1);


INSERT INTO User
(email_address, first_name, last_name, nick_name)
VALUES ('taxi@gmail.com', 'renzhentaxi', 'baerde', 'taxi');

INSERT INTO Sender
(userId, password, sessionId) VALUES (1, 'no password', 0);

INSERT INTO MailTypes
(templateId, name, location, dateUpdated)
VALUES (1,'Chase','Data/Mails/Chase/index.html', '2018-02-28');
