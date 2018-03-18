SELECT exists
(
    SELECT userId
    FROM
      Sender
    WHERE userId = :id
    LIMIT 1
);