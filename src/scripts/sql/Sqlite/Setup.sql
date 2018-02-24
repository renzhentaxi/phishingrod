PRAGMA FOREIGN_KEYS = ON;

DROP TABLE IF EXISTS Sender;
DROP TABLE IF EXISTS User;


CREATE TABLE User
(
  userId        INTEGER PRIMARY KEY             NOT NULL,
  email_address TEXT UNIQUE                     NOT NULL,
  first_name    TEXT                            NOT NULL,
  last_name     TEXT                            NOT NULL,
  nick_name     TEXT

);

CREATE TABLE Sender
(
  userId      INTEGER PRIMARY KEY REFERENCES User (userid),
  password    TEXT NOT NULL,
  sessionType TEXT NOT NULL
);