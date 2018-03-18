SELECT
  User.userId,
  email_address,
  first_name,
  last_name,
  nick_name,
  password,
  sessionTypeName
FROM User
  INNER JOIN Sender ON User.userId = Sender.userId
WHERE User.userId = :id;
