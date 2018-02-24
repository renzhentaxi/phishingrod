UPDATE User
SET
  first_name = :firstName,
  last_name  = :lastName,
  nick_name  = :nickName
WHERE
  email_address = :emailAddress;