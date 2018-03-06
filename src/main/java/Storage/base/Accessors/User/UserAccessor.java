package Storage.base.Accessors.User;

import Entities.Users.User;
import Entities.Users.UserEntity;
import Storage.base.Accessors.AccessorUtil;
import Storage.base.Accessors.Exceptions.EntityAlreadyExistException;
import Storage.base.Accessors.Exceptions.EntityDoesNotExistException;
import Storage.base.Accessors.Exceptions.EntityUpdateException;
import Storage.base.Accessors.IWithHandleAccessor;
import Storage.base.Util.AlternativeSqlLocator;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;

/**
 * the _with methods do not close the handle.
 * they are used when other accessors needs to access a user object
 */
public class UserAccessor implements IUserAccessor, IWithHandleAccessor<UserEntity, User>
{
    private Jdbi jdbi;
    private String getQuery;
    private String getByEmailQuery;
    private String addQuery;
    private String updateQuery;

    public UserAccessor(Jdbi jdbi, AlternativeSqlLocator locator)
    {
        this.jdbi = jdbi;

        getQuery = locator.locate("getUser");
        getByEmailQuery = locator.locate("getUserByEmail");
        addQuery = locator.locate("addUser");
        updateQuery = locator.locate("updateUser");
    }

    @Override
    public UserEntity add(User data)
    {
        try (Handle handle = jdbi.open())
        {
            return addWith(data, handle);
        }
    }


    @Override
    public UserEntity get(int id)
    {
        try (Handle handle = jdbi.open())
        {
            return handle.createQuery(getQuery).bind("id", id).mapTo(UserEntity.class).findOnly();
        } catch (IllegalStateException exception)
        {
            if (exception.getMessage().equals("No element found in 'only'"))
                throw new EntityDoesNotExistException("No user with id: " + id + " in database");
            else throw exception;
        }
    }

    @Override
    public UserEntity getByEmail(String emailAddress)
    {
        try (Handle handle = jdbi.open())
        {
            return handle.createQuery(getByEmailQuery).bind("emailAddress", emailAddress).mapTo(UserEntity.class).findOnly();
        } catch (IllegalStateException exception)
        {
            if (exception.getMessage().equals("No element found in 'only'"))
                throw new EntityDoesNotExistException("No user with email: " + emailAddress + " in database");
            else throw exception;
        }
    }

    @Override
    public void update(UserEntity user)
    {
        try (Handle handle = jdbi.open())
        {
            updateWith(user, handle);
        }
    }


    @Override
    public UserEntity addWith(User data, Handle handle)
    {
        try
        {
            int id = handle.createUpdate(addQuery).bindBean(data).executeAndReturnGeneratedKeys().mapTo(Integer.class).findOnly();
            return new UserEntity(id, data);
        } catch (UnableToExecuteStatementException exception)
        {
            if (AccessorUtil.isExceptionDueToEntityAlreadyExist(exception, "email_address"))
                throw new EntityAlreadyExistException();
            else throw exception;
        }
    }

    @Override
    public void updateWith(UserEntity user, Handle handle)
    {
        try
        {
            if (handle.createUpdate(updateQuery).bindBean(user).execute() != 1)
            {
                throw new EntityDoesNotExistException("Can not find user: " + user.toString() + " in database");
            }
        } catch (UnableToExecuteStatementException exception)
        {
            if (AccessorUtil.isExceptionDueToEntityAlreadyExist(exception, "email_address"))
                throw new EntityUpdateException();
            else throw exception;
        }
    }
}
