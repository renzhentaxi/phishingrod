UPDATE User
SET
  email_address = :emailAddress,
  first_name    = :firstName,
  last_name     = :lastName,
  nick_name     = :nickName
WHERE
  userId = :id;