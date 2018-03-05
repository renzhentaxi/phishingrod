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
  userId       INTEGER PRIMARY KEY REFERENCES User (userid),
  password     TEXT NOT NULL,
  session_Type TEXT REFERENCES SessionType (name)
);

CREATE TABLE IF NOT EXISTS SessionType
(
  name PRIMARY KEY  NOT NULL,
  host TEXT         NOT NULL,
  port INTEGER      NOT NULL,
  auth BOOLEAN      NOT NULL,
  tls  BOOLEAN      NOT NULL
);

CREATE TABLE IF NOT EXISTS MailTypes
(
  templateId            INTEGER PRIMARY KEY ,
  name          TEXT            NOT NULL,
  location          TEXT        NOT NULL ,
  dateUpdated             date        NOT NULL
)

