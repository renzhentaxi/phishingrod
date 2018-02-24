package Storage.Sqlite;

import Accounts.User;
import Storage.base.UserDao;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface SqliteUserDao extends UserDao
{
    @Override
    @SqlQuery("Sqlite/getUser")
    User get(String emailAddress);

    @Override
    @SqlUpdate("Sqlite/addUser")
    void add(@BindBean User user);

    @Override
    @SqlUpdate("Sqlite/updateUser")
    void update(@BindBean User user);
}
