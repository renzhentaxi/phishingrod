package Storage.Sqlite;

import Storage.ConnectionProvider;
import Storage.UserAccessor;

public class SqliteUserAccessor extends UserAccessor
{
    private static final String getQuery = "SELECT * FROM User WHERE email_address = ?;";
    private static final String addQuery = "";//todo
    private static final String updateQuery = "";//todo

    public SqliteUserAccessor(ConnectionProvider connectionProvider)
    {
        super(connectionProvider);
    }

    @Override
    protected String getGetQueryString()
    {
        return getQuery;
    }

    @Override
    protected String getAddQueryString()
    {
        return addQuery;
    }

    @Override
    protected String getUpdateQueryString()
    {
        return updateQuery;
    }

}
