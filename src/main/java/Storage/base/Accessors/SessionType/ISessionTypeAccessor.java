package Storage.base.Accessors.SessionType;

import Entities.SessionTypes.ISessionType;
import Entities.SessionTypes.ISessionTypeEntity;
import Storage.base.Accessors.Exceptions.EntityDoesNotExistException;
import Storage.base.Accessors.IAccessor;

public interface ISessionTypeAccessor extends IAccessor<ISessionTypeEntity, ISessionType>
{
    /**
     * retrieve the sessionType with the given name
     *
     * @param name the name of the sessionType
     * @return the sessionType
     * @throws EntityDoesNotExistException if there are not sessionType with the given name.
     */
    ISessionTypeEntity getByName(String name);

    /**
     * check whether a sessionType with the name exist in database
     * @param name the name of the sessionType to check
     * @return true if a sessionType with the name exist in database, false otherwise
     */
    boolean exist(String name);
}
