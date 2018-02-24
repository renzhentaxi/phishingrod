package Storage.base;

import Accounts.User;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

public abstract class UserAccessor implements UserDao
{

    protected abstract String getGetQueryString();

    protected abstract String getAddQueryString();

    protected abstract String getUpdateQueryString();

    private final Jdbi _jdbi;

    public UserAccessor(Jdbi jdbi)
    {
        _jdbi = jdbi;
    }

    @Override
    public User get(String address)
    {
        try (Handle handle = _jdbi.open())
        {
            return handle
                    .createQuery(getGetQueryString())
                    .bind("emailAddress", address)
                    .mapTo(User.class)
                    .findOnly();
        }
    }

    @Override
    public void add(User user)
    {
        try (Handle handle = _jdbi.open())
        {
            handle
                    .createUpdate(getAddQueryString())
                    .bindBean(user)
                    .execute();
        }
    }

    @Override
    public void update(User user)
    {

        try (Handle handle = _jdbi.open())
        {
            handle
                    .createUpdate(getUpdateQueryString())
                    .bindBean(user)
                    .execute();
        }
    }


}
