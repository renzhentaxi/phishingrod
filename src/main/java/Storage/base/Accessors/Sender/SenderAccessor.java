package Storage.base.Accessors.Sender;

import Entities.Senders.ISender;
import Entities.Senders.ISenderEntity;
import Entities.Senders.SenderEntity;
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
    private IUserAccessor userAccessor;
    private ISessionTypeAccessor sessionTypeAccessor;

    private String getQuery;
    private String getByEmailQuery;
    private String addQuery;
    private String updateQuery;
    private String existQuery;
    private String existByEmailQuery;


    public SenderAccessor(Jdbi jdbi, AlternativeSqlLocator sqlLocator, IUserAccessor userAccessor, ISessionTypeAccessor sessionTypeAccessor)
    {
        this.jdbi = jdbi;
        this.userAccessor = userAccessor;
        this.sessionTypeAccessor = sessionTypeAccessor;

        this.getQuery = sqlLocator.locate("getSender");
        this.getByEmailQuery = sqlLocator.locate("getSenderByEmail");
        this.addQuery = sqlLocator.locate("addSender");
        this.updateQuery = sqlLocator.locate("updateSender");
        this.existQuery = sqlLocator.locate("existSender");
        this.existByEmailQuery = sqlLocator.locate("existSenderByEmail");
    }

    @Override
    public boolean exist(int id)
    {
        return jdbi.withHandle(handle -> handle.createQuery(existQuery).bind("id", id).mapTo(Boolean.class).findOnly());
    }

    @Override
    public boolean exist(String emailAddress)
    {
        return jdbi.withHandle(handle -> handle.createQuery(existByEmailQuery).bind("email_address", emailAddress).mapTo(Boolean.class).findOnly());
    }

    @Override
    public ISenderEntity get(int id)
    {
        if (!exist(id)) throw new EntityDoesNotExistException("Sender", "id", id);
        return jdbi.withHandle(handle ->
                handle.createQuery(getQuery).bind("id", id).mapTo(SenderEntity.class).findOnly());
    }

    @Override
    public ISenderEntity getByEmail(String emailAddress)
    {
        if (!exist(emailAddress)) throw new EntityDoesNotExistException("Sender", "email", emailAddress);

        return jdbi.withHandle(handle -> handle.createQuery(getByEmailQuery).bind("emailAddress", emailAddress).mapTo(SenderEntity.class).findOnly());
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
        return jdbi.withHandle(handle -> addWith(data, handle));
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
        jdbi.useHandle(handle -> updateWith(entity, handle));
    }

    @Override
    public ISenderEntity addWith(ISender data, Handle handle)
    {
        if (userAccessor.exist(data.getEmailAddress()))
            throw new EntityAlreadyExistException("user", "email Address", data.getEmailAddress(), data);
        else if (exist(data.getEmailAddress()))
            throw new EntityAlreadyExistException("sender", "email Address", data.getEmailAddress(), data);
        else if (!sessionTypeAccessor.exist(data.getSessionTypeName()))
            throw new EntityDoesNotExistException("sessionType", "name", data.getSessionTypeName());

        return handle.inTransaction(h ->
                {
                    int id = userAccessor.addWith(data, handle).getId();
                    h.createUpdate(addQuery).bindBean(data).bind("id", id).execute();
                    return new SenderEntity(id, data);
                }
        );

    }

    @Override
    public void updateWith(ISenderEntity entity, Handle handle)
    {
        if (!sessionTypeAccessor.exist(entity.getSessionTypeName()))
            throw new EntityDoesNotExistException("sessionType", "name", entity.getSessionTypeName());
        else if (!exist(entity.getId()))
            throw new EntityDoesNotExistException("sender", "id", entity.getId());
        handle.useTransaction(h ->
                {
                    userAccessor.updateWith(entity, h);
                    int updatedCount = h.createUpdate(updateQuery).bindBean(entity).execute();
                    System.out.println(updatedCount);
                }
        );
    }
}
