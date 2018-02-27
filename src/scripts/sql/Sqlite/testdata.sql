INSERT INTO SessionType
(name, host, port, auth, tls) VALUES ('gmail', 'smtp.gmail.com', 587, 1, 1);


INSERT INTO User
(email_address, first_name, last_name, nick_name)
VALUES ('taxi@gmail.com', 'renzhentaxi', 'baerde', 'taxi');

INSERT INTO Sender
(userId, password, session_Type) VALUES (1, 'no password', 'gmail');

