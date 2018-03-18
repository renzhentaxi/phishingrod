SELECT exists(
    SELECT userId
    FROM User
    WHERE email_address = :emailAddress
    LIMIT 1
);