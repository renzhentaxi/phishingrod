package Storage.base;

import Accounts.User;
import Storage.base.JdbiUtil.StoredSqlLocator;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

public class UserAccessor implements UserDao {
    private String getQuery;
    private String addQuery;
    private String updateQuery;

    private final Jdbi _jdbi;

    public UserAccessor(Jdbi jdbi, StoredSqlLocator locator) {
        _jdbi = jdbi;
        getQuery = locator.locate("getUser");
        addQuery = locator.locate("addUser");
        updateQuery = locator.locate("updateUser");
    }

    @Override
    public User get(String address) {
        try (Handle handle = _jdbi.open()) {
            return handle
                    .createQuery(getQuery)
                    .bind("emailAddress", address)
                    .mapTo(User.class)
                    .findOnly();
        }
    }

    @Override
    public void add(User user) {
        try (Handle handle = _jdbi.open()) {
            handle
                    .createUpdate(addQuery)
                    .bindBean(user)
                    .execute();
        }
    }

    @Override
    public void update(User user) {

        try (Handle handle = _jdbi.open()) {
            handle
                    .createUpdate(updateQuery)
                    .bindBean(user)
                    .execute();
        }
    }


}
