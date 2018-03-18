package Storage.base.Accessors.Sender;

import Entities.Senders.ISender;
import Entities.Senders.ISenderEntity;
import Storage.base.Accessors.Exceptions.EntityAlreadyExistException;
import Storage.base.Accessors.Exceptions.EntityDoesNotExistException;
import Storage.base.Accessors.Exceptions.EntityUpdateException;
import Storage.base.Accessors.SessionType.ISessionTypeAccessor;
import Storage.base.Accessors.User.IUserAccessor;
import Storage.base.Util.AlternativeSqlLocator;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

public class SenderAccessor implements ISenderAccessor
{
    private Jdbi jdbi;
    private String getQuery;
    private String getByEmailQuery;
    private String addQuery;
    private String updateQuery;
    private IUserAccessor userAccessor;
    private ISessionTypeAccessor sessionTypeAccessor;

    public SenderAccessor(Jdbi jdbi, AlternativeSqlLocator sqlLocator, IUserAccessor userAccessor, ISessionTypeAccessor sessionTypeAccessor)
    {
        this.jdbi = jdbi;
        this.getQuery = sqlLocator.locate("getSender");
        this.getByEmailQuery = sqlLocator.locate("getSenderByEmail");
        this.addQuery = sqlLocator.locate("addSender");
        this.updateQuery = sqlLocator.locate("updateSender");
        this.userAccessor = userAccessor;
        this.sessionTypeAccessor = sessionTypeAccessor;

    }

    @Override
    public boolean exist(int id)
    {
        return false;
    }

    @Override
    public boolean exist(String emailAddress)
    {
        return false;
    }

    @Override
    public ISenderEntity get(int id)
    {
        return null;
    }

    @Override
    public ISenderEntity getByEmail(String emailAddress)
    {
        return null;
    }

    /**
     * adds the sender to the database
     * note: in the database, it will add both a new user entry as well as a new sender entry
     *
     * @param data the data object to be added to the database
     * @return the sender object in entity form
     * @throws EntityAlreadyExistException if a given user already exist in the database
     *                                     note: it is also possible for a sessionType to exist in the database, but in order for a sessionType to exist
     *                                     the user must also exist and therefore we dont need a separate exception.
     * @throws EntityDoesNotExistException if there is no sessionType with the id in the database
     */
    @Override
    public ISenderEntity add(ISender data)
    {
        if (userAccessor.exist(data.getEmailAddress()))
            throw new EntityAlreadyExistException("sender", "email Address", data.getEmailAddress(), data);
        return null;
    }

    /**
     * updates the sender in the database with the given entity
     * note: it will update both the user entry as well as the sender entry in the database
     * since a sender is an extension of a user
     *
     * @param entity the modified entity to be synced
     * @throws EntityDoesNotExistException if no sessionType with the id exist
     * @throws EntityDoesNotExistException if no sender with the id exist
     * @throws EntityUpdateException       if an user with the same email already exist in the database
     */
    @Override
    public void update(ISenderEntity entity)
    {
    }

    @Override
    public ISenderEntity addWith(ISender data, Handle handle)
    {
        return null;
    }

    @Override
    public void updateWith(ISenderEntity entity, Handle handle)
    {

    }
}
