package Storage;

import java.sql.Connection;

public interface ConnectionProvider
{
    Connection getConnection();
}
