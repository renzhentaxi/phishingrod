UPDATE Sender
SET
  password     = :password,
  session_Type = :sessionType
WHERE
  userId = :id;

