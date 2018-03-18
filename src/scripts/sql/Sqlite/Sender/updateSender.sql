UPDATE Sender
SET
  password        = :password,
  sessionTypeName = :sessionTypeName
WHERE
  userId = :id;

