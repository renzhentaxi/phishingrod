SELECT exists(
    SELECT userId
    FROM User
    WHERE userId = :id
    LIMIT 1
);