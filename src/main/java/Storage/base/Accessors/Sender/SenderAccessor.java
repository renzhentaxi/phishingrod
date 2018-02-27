package Storage.base.Accessors.Sender;

import Entities.Data.SenderData;
import Entities.Sender;
import Storage.base.Accessors.ExceptionHandler;
import Storage.base.Accessors.User.UserAccessor;
import Storage.base.Util.AlternativeSqlLocator;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;

public class SenderAccessor implements ISenderAccessor
{
    private Jdbi jdbi;
    private ExceptionHandler exceptionHandler;
    private UserAccessor userAccessor;
    private String getQuery;
    private String getByEmailQuery;
    private String addQuery;
    private String updateQuery;

    public SenderAccessor(Jdbi jdbi, ExceptionHandler exceptionHandler, AlternativeSqlLocator locator, UserAccessor userAccessor)
    {
        this.jdbi = jdbi;
        this.exceptionHandler = exceptionHandler;
        this.userAccessor = userAccessor;
        getQuery = locator.locate("getSender");
        getByEmailQuery = locator.locate("getSenderByEmail");
        addQuery = locator.locate("addSender");
        updateQuery = locator.locate("updateSender");
    }

    @Override
    public Sender getByEmail(String emailAddress)
    {
        try (Handle handle = jdbi.open())
        {
            return getByEmailWith(emailAddress, handle);
        }
    }

    @Override
    public Sender get(int id)
    {
        try (Handle handle = jdbi.open())
        {
            return getWith(id, handle);
        }
    }

    @Override
    public Sender add(SenderData data)
    {
        try (Handle handle = jdbi.open())
        {
            return addWith(data, handle);
        }
    }

    @Override
    public void update(Sender entity)
    {
        try (Handle handle = jdbi.open())
        {
            updateWith(entity, handle);
        }

    }


    public Sender getByEmailWith(String emailAddress, Handle handle)
    {
        try
        {
            return handle.createQuery(getByEmailQuery).bind("emailAddress", emailAddress).mapTo(Sender.class).findOnly();
        } catch (UnableToExecuteStatementException exception)
        {
            exception.printStackTrace();
            throw exception;
        }
    }

    public Sender getWith(int id, Handle handle)
    {
        try
        {
            return handle.createQuery(getQuery).bind("id", id).mapTo(Sender.class).findOnly();
        } catch (UnableToExecuteStatementException exception)
        {
            exception.printStackTrace();
            throw exception;
        }
    }

    public Sender addWith(SenderData data, Handle handle)
    {
        try
        {
            handle.createUpdate(addQuery).bindBean(data).bind("id", data.getUser().getId()).execute();
            return new Sender(data);
        } catch (UnableToExecuteStatementException exception)
        {
            throw exceptionHandler.Handle(exception);
        }
    }

    public void updateWith(Sender entity, Handle handle)
    {
        try
        {
            userAccessor.updateWith(entity.getUser(), handle);
            handle.createUpdate(updateQuery).bindBean(entity).execute();
        } catch (UnableToExecuteStatementException exception)
        {
            throw exceptionHandler.Handle(exception);
        }

    }
}
