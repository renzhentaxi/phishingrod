UPDATE SessionType
SET
  name = :name,
  host = :host,
  port = :port,
  auth = :auth,
  tls  = :tls
WHERE
  sessionId = :id;
