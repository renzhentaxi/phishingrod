SELECT exists
(
    SELECT user.userId
    FROM
      Sender
      INNER JOIN User ON Sender.userId = User.userId
    WHERE email_address = :email_address
    LIMIT 1
) as 'exist';

SELECT exists
(
    SELECT user.userId
    FROM
      Sender
      INNER JOIN User ON Sender.userId = User.userId
    WHERE email_address = 'taxi@gmail.com'
    LIMIT 1
)AS 'exist';