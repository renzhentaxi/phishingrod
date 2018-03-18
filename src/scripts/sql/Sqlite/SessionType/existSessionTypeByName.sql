SELECT exists(
    SELECT sessionId
    FROM SessionType
    WHERE name = :name
    LIMIT 1
);