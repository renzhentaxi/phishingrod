package Storage.base.Accessors.SessionType;

import Entities.SessionTypes.ISessionType;
import Entities.SessionTypes.ISessionTypeEntity;
import Entities.SessionTypes.SessionTypeEntity;
import Storage.base.Accessors.AccessorUtil;
import Storage.base.Accessors.Exceptions.EntityAlreadyExistException;
import Storage.base.Accessors.Exceptions.EntityDoesNotExistException;
import Storage.base.Accessors.Exceptions.EntityUpdateException;
import Storage.base.Accessors.IWithHandleAccessor;
import Storage.base.Util.AlternativeSqlLocator;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;

public class SessionTypeAccessor implements ISessionTypeAccessor, IWithHandleAccessor<ISessionTypeEntity, ISessionType>
{
    private Jdbi jdbi;
    private String getQuery;
    private String getByNameQuery;
    private String addQuery;
    private String updateQuery;
    private String existQuery;
    private String existByNameQuery;

    public SessionTypeAccessor(Jdbi jdbi, AlternativeSqlLocator alternativeSqlLocator)
    {
        this.jdbi = jdbi;
        getQuery = alternativeSqlLocator.locate("getSessionType");
        getByNameQuery = alternativeSqlLocator.locate("getSessionTypeByName");
        addQuery = alternativeSqlLocator.locate("addSessionType");
        updateQuery = alternativeSqlLocator.locate("updateSessionType");
        existQuery = alternativeSqlLocator.locate("existSessionType");
        existByNameQuery = alternativeSqlLocator.locate("existSessionTypeByName");
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
    public boolean exist(String name)
    {
        try (Handle handle = jdbi.open())
        {
            return handle.createQuery(existByNameQuery).bind("name", name).mapTo(Boolean.class).findOnly();
        }
    }

    @Override
    public ISessionTypeEntity get(int id)
    {
        try (Handle handle = jdbi.open())
        {
            return handle.createQuery(getQuery).bind("id", id).mapTo(SessionTypeEntity.class).findOnly();
        } catch (IllegalStateException exception)
        {
            if (exception.getMessage().equals("No element found in 'only'"))
                throw new EntityDoesNotExistException("sessionType", "id", id);
            else throw exception;
        }
    }

    @Override
    public ISessionTypeEntity getByName(String name)
    {
        try (Handle handle = jdbi.open())
        {
            return handle.createQuery(getByNameQuery).bind("name", name).mapTo(SessionTypeEntity.class).findOnly();
        } catch (IllegalStateException exception)
        {
            if (exception.getMessage().equals("No element found in 'only'"))
                throw new EntityDoesNotExistException("sessionType", "name", name);
            else throw exception;
        }
    }

    @Override
    public ISessionTypeEntity add(ISessionType data)
    {
        try (Handle handle = jdbi.open())
        {
            return addWith(data, handle);
        }
    }

    @Override
    public void update(ISessionTypeEntity entity)
    {
        try (Handle handle = jdbi.open())
        {
            updateWith(entity, handle);
        }
    }

    @Override
    public ISessionTypeEntity addWith(ISessionType data, Handle handle)
    {
        try
        {
            int id = handle.createUpdate(addQuery).bindBean(data).executeAndReturnGeneratedKeys().mapTo(Integer.class).findOnly();
            return new SessionTypeEntity(id, data);
        } catch (UnableToExecuteStatementException exception)
        {
            if (AccessorUtil.isExceptionDueToEntityAlreadyExist(exception, "name"))
                throw new EntityAlreadyExistException("SessionType", "name", data.getName(), data);
            else throw exception;
        }
    }

    @Override
    public void updateWith(ISessionTypeEntity entity, Handle handle)
    {
        try
        {
            if (handle.createUpdate(updateQuery).bindBean(entity).execute() != 1)
            {
                throw new EntityDoesNotExistException("sessionType", "name", entity.getName());
            }
        } catch (UnableToExecuteStatementException exception)
        {
            if (AccessorUtil.isExceptionDueToEntityAlreadyExist(exception, "name"))
                throw new EntityUpdateException();
            else throw exception;
        }
    }
}
