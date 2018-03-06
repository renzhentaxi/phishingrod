SELECT
  User.userId,
  email_address,
  first_name,
  last_name,
  nick_name,
  password,
  session_Type
FROM User
  INNER JOIN Sender ON User.userId = Sender.userId
WHERE email_address = :emailAddress;
