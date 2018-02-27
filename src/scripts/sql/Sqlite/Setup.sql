PRAGMA FOREIGN_KEYS = ON;

DROP TABLE IF EXISTS Sender;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS SessionType;

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
  userId       INTEGER PRIMARY KEY REFERENCES User (userid),
  password     TEXT NOT NULL,
  session_Type TEXT REFERENCES SessionType (name)
);

CREATE TABLE SessionType
(
  name PRIMARY KEY  NOT NULL,
  host TEXT         NOT NULL,
  port INTEGER      NOT NULL,
  auth BOOLEAN      NOT NULL,
  tls  BOOLEAN      NOT NULL
);

