package Storage.base.Daos;

import Accounts.User;
import Storage.base.Accessors.DatabaseAccessor;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UserDao extends DatabaseAccessor<User> {
    @Override
    @SqlQuery("getUser")
    User get(@Bind("emailAddress") String emailAddress);

    @Override
    @SqlUpdate("addUser")
    void add(@BindBean User user);

    @Override
    @SqlUpdate("updateUser")
    void update(@BindBean User user);
}
