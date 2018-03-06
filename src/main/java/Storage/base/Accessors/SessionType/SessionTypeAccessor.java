package Storage.base.Accessors.SessionType;

import Entities.SessionTypes.ISessionType;
import Entities.SessionTypes.ISessionTypeEntity;
import org.jdbi.v3.core.Jdbi;

public class SessionTypeAccessor implements ISessionTypeAccessor
{
    private Jdbi jdbi;
    private String getQuery;
    private String getByNameQuery;
    private String addQuery;
    private String updateQuery;


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


}
