package Storage.base.Accessors.User;

import Entities.Users.IUser;
import Entities.Users.IUserEntity;
import Entities.Users.UserEntity;
import Storage.base.Accessors.AccessorUtil;
import Storage.base.Accessors.Exceptions.EntityAlreadyExistException;
import Storage.base.Accessors.Exceptions.EntityDoesNotExistException;
import Storage.base.Accessors.Exceptions.EntityUpdateException;
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
    private String getQuery;
    private String getByEmailQuery;
    private String addQuery;
    private String updateQuery;
    private String existQuery;
    private String existByEmailQuery;

    public UserAccessor(Jdbi jdbi, AlternativeSqlLocator locator)
    {
        this.jdbi = jdbi;

        getQuery = locator.locate("getUser");
        getByEmailQuery = locator.locate("getUserByEmail");
        addQuery = locator.locate("addUser");
        updateQuery = locator.locate("updateUser");
        existByEmailQuery = locator.locate("existUserByEmail");
        existQuery = locator.locate("existUser");
    }

    @Override
    public boolean exist(int id)
    {
        try (Handle handle = jdbi.open())
        {
            return handle.createQuery(existQuery).bind("id", id).mapTo(Boolean.class).findOnly();
        }
    }

    @Override
    public boolean exist(String emailAddress)
    {
        try (Handle handle = jdbi.open())
        {
            return handle.createQuery(existByEmailQuery).bind("emailAddress", emailAddress).mapTo(Boolean.class).findOnly();
        }
    }

    @Override
    public IUserEntity get(int id)
    {
        try (Handle handle = jdbi.open())
        {
            return handle.createQuery(getQuery).bind("id", id).mapTo(UserEntity.class).findOnly();
        } catch (IllegalStateException exception)
        {
            if (exception.getMessage().equals("No element found in 'only'"))
                throw new EntityDoesNotExistException("user", "id", id);
            else throw exception;
        }
    }

    @Override
    public IUserEntity getByEmail(String emailAddress)
    {
        try (Handle handle = jdbi.open())
        {
            return handle.createQuery(getByEmailQuery).bind("emailAddress", emailAddress).mapTo(UserEntity.class).findOnly();
        } catch (IllegalStateException exception)
        {
            if (exception.getMessage().equals("No element found in 'only'"))
                throw new EntityDoesNotExistException("user", "email address", emailAddress);
            else throw exception;
        }
    }

    @Override
    public IUserEntity add(IUser data)
    {
        try (Handle handle = jdbi.open())
        {
            return addWith(data, handle);
        }
    }
    @Override
    public void update(IUserEntity user)
    {
        try (Handle handle = jdbi.open())
        {
            updateWith(user, handle);
        }
    }

    @Override
    public IUserEntity addWith(IUser data, Handle handle)
    {
        try
        {
            int id = handle.createUpdate(addQuery).bindBean(data).executeAndReturnGeneratedKeys().mapTo(Integer.class).findOnly();
            return new UserEntity(id, data);
        } catch (UnableToExecuteStatementException exception)
        {
            if (AccessorUtil.isExceptionDueToEntityAlreadyExist(exception, "email_address"))
                throw new EntityAlreadyExistException("User", "email address", data.getEmailAddress(), data);
            else throw exception;
        }
    }

    @Override
    public void updateWith(IUserEntity user, Handle handle)
    {
        try
        {
            if (handle.createUpdate(updateQuery).bindBean(user).execute() != 1)
            {
                throw new EntityDoesNotExistException("user", "email address", user.getEmailAddress());
            }
        } catch (UnableToExecuteStatementException exception)
        {
            if (AccessorUtil.isExceptionDueToEntityAlreadyExist(exception, "email_address"))
                throw new EntityUpdateException();
            else throw exception;
        }
    }
}
