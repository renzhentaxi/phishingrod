package Storage.base.Accessors.User;

import Accounts.Entities.User;
import Accounts.Entities.Data.UserData;
import Storage.base.Accessors.ExceptionHandler;
import Storage.base.Accessors.Exceptions.EntityDoesNotExistException;
import Storage.base.Accessors.User.IUserAccessor;
import Storage.base.Util.AlternativeSqlLocator;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;

/**
 * the _with methods do not close the handle.
 * they are used when other accessors needs to access a user object
 */
public class UserAccessor implements IUserAccessor
{
    private Jdbi jdbi;
    private ExceptionHandler exceptionHandler;
    private String getQuery;
    private String getByEmailQuery;
    private String addQuery;
    private String updateQuery;

    public UserAccessor(Jdbi jdbi, ExceptionHandler exceptionHandler, AlternativeSqlLocator locator)
    {
        this.jdbi = jdbi;
        this.exceptionHandler = exceptionHandler;

        getQuery = locator.locate("getUser");
        getByEmailQuery = locator.locate("getUserByEmail");
        addQuery = locator.locate("addUser");
        updateQuery = locator.locate("updateUser");
    }

    @Override
    public User add(UserData data)
    {
        try (Handle handle = jdbi.open())
        {
            return addWith(data, handle);
        }
    }

    @Override
    public User get(int id)
    {
        try (Handle handle = jdbi.open())
        {
            return getWith(id, handle);
        }
    }

    @Override
    public User getByEmail(String emailAddress)
    {
        try (Handle handle = jdbi.open())
        {
            return getByEmailWith(emailAddress, handle);
        }
    }

    @Override
    public void update(User user)
    {
        try (Handle handle = jdbi.open())
        {
            updateWith(user, handle);
        }
    }


    public User addWith(UserData data, Handle handle)
    {
        try
        {
            int id = handle.inTransaction(h ->
                    handle.createUpdate(addQuery).bindBean(data).executeAndReturnGeneratedKeys().mapTo(Integer.class).findOnly());
            return new User(id, data);
        } catch (UnableToExecuteStatementException exception)
        {
            throw exceptionHandler.Handle(exception);
        }
    }

    public User getWith(int id, Handle handle)
    {
        try
        {
            return handle.createQuery(getQuery).bind("id", id).mapTo(User.class).findOnly();
        } catch (UnableToExecuteStatementException exception)
        {
            throw exceptionHandler.Handle(exception);
        } catch (IllegalStateException exception)
        {
            if (exception.getMessage().equals("No element found in 'only'"))
                throw new EntityDoesNotExistException();
            else throw exception;
        }

    }

    public User getByEmailWith(String emailAddress, Handle handle)
    {
        try
        {
            return handle.createQuery(getByEmailQuery).bind("emailAddress", emailAddress).mapTo(User.class).findOnly();
        } catch (UnableToExecuteStatementException exception)
        {
            throw exceptionHandler.Handle(exception);
        } catch (IllegalStateException exception)
        {
            if (exception.getMessage().equals("No element found in 'only'"))
                throw new EntityDoesNotExistException();
            else throw exception;
        }
    }

    public void updateWith(User user, Handle handle)
    {
        try
        {
            handle.createUpdate(updateQuery).bindBean(user).execute();
        } catch (UnableToExecuteStatementException exception)
        {
            throw exceptionHandler.Handle(exception);
        }
    }
}
