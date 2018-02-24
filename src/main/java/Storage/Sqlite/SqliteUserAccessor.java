package Storage.Sqlite;

import Storage.base.JdbiUtil.FilePathSqlLocator;
import Storage.base.UserAccessor;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;

public class SqliteUserAccessor extends UserAccessor
{
    private static final String getQuery = "SELECT * FROM User WHERE email_address = :emailAddress;";
    private static final String addQuery = FilePathSqlLocator.locate("Sqlite/addUser");
    private static final String updateQuery = FilePathSqlLocator.locate("Sqlite/updateUser");

    @Inject
    public SqliteUserAccessor(Jdbi jdbi)
    {
        super(jdbi);
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
