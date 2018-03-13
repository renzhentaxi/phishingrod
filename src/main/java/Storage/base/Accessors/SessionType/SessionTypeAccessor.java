package Storage.base.Accessors.SessionType;

import Entities.SessionTypes.ISessionType;
import Entities.SessionTypes.ISessionTypeEntity;
import Storage.base.Accessors.IWithHandleAccessor;
import Storage.base.Util.AlternativeSqlLocator;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

public class SessionTypeAccessor implements ISessionTypeAccessor, IWithHandleAccessor<ISessionTypeEntity, ISessionType>
{
    private Jdbi jdbi;
    private String getQuery;
    private String getByNameQuery;
    private String addQuery;
    private String updateQuery;

    public SessionTypeAccessor(Jdbi jdbi, AlternativeSqlLocator alternativeSqlLocator)
    {
        this.jdbi = jdbi;
        getQuery = alternativeSqlLocator.locate("getSessionType");
        getByNameQuery = alternativeSqlLocator.locate("getSessionTypeByName");
        addQuery = alternativeSqlLocator.locate("addSessionType");
        updateQuery = alternativeSqlLocator.locate("updateSessionType");
    }

    @Override
    public ISessionTypeEntity get(int id)
    {
        return null;
    }

    @Override
    public ISessionTypeEntity getByName(String name)
    {
        return null;
    }

    @Override
    public ISessionTypeEntity add(ISessionType data)
    {
        return null;
    }

    @Override
    public void update(ISessionTypeEntity entity)
    {

    }

    @Override
    public ISessionTypeEntity addWith(ISessionType data, Handle handle)
    {
        return null;
    }

    @Override
    public void updateWith(ISessionTypeEntity entity, Handle handle)
    {

    }
}
