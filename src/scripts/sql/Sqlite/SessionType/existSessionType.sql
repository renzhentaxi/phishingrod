SELECT exists(
    SELECT sessionId
    FROM SessionType
    WHERE sessionId = :id
    LIMIT 1
);