PRAGMA FOREIGN_KEYS = ON;

CREATE TABLE IF NOT EXISTS User
(
  userId        INTEGER PRIMARY KEY             NOT NULL,
  email_address TEXT UNIQUE                     NOT NULL,
  first_name    TEXT                            NOT NULL,
  last_name     TEXT                            NOT NULL,
  nick_name     TEXT

);


CREATE TABLE IF NOT EXISTS Sender
(
  userId    INTEGER PRIMARY KEY REFERENCES User (userid),
  password  TEXT NOT NULL,
  sessionId INTEGER REFERENCES SessionType (sessionId)
);

CREATE TABLE IF NOT EXISTS SessionType
(
  sessionId INTEGER PRIMARY KEY,
  name      TEXT UNIQUE NOT NULL,
  host      TEXT        NOT NULL,
  port      INTEGER     NOT NULL,
  auth      BOOLEAN     NOT NULL,
  tls       BOOLEAN     NOT NULL
);

CREATE TABLE IF NOT EXISTS MailTypes
(
  templateId  INTEGER PRIMARY KEY,
  name        TEXT UNIQUE NOT NULL,
  location    TEXT        NOT NULL,
  dateUpdated DATE        NOT NULL
)

