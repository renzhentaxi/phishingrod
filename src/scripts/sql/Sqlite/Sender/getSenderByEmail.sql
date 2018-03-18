SELECT
  User.userId AS id,
  email_address,
  first_name,
  last_name,
  nick_name,
  password,
  sessionTypeName
FROM User
  INNER JOIN Sender ON User.userId = Sender.userId
WHERE email_address = :emailAddress;

