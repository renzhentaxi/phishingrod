package Storage.Sqlite;

import Storage.ConnectionProvider;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class SqliteConnectionProvider implements ConnectionProvider
{
    private final SQLiteDataSource _dataSource;

    public SqliteConnectionProvider(String databaseLocation)
    {
        _dataSource = new SQLiteDataSource();
        _dataSource.setUrl("jdbc:sqlite:" + databaseLocation);
    }

    @Override
    public Connection getConnection()
    {
        Connection connection;
        try
        {
            connection = _dataSource.getConnection();
            return connection;
        } catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        throw new RuntimeException("Connection failed!");
    }
}
