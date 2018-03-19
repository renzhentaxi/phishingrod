package Storage.base.Accessors;

import Storage.base.Accessors.Exceptions.EntityAlreadyExistException;
import Storage.base.Accessors.Exceptions.EntityDoesNotExistException;
import Storage.base.Accessors.Exceptions.EntityUpdateException;
import org.jdbi.v3.core.Handle;

/**
 * typically everytime you access an object using IAccessor, a new connection/handle is created and then closed right after.
 * however there are times when you want to do multiple operations such as add a userEntity and add a senderEntity
 * using the same connection/handle for transaction control
 * e.g: if adding user succeeded but adding sender failed. we would want to rollback the user we added.
 */
public interface IWithHandleAccessor<E, D> extends IAccessor<E, D>
{
    /**
     * adds the data object to database using the handle.
     *
     * @param data   the data object to be added
     * @param handle the handle to use to execute the command to insert the data
     * @return the entity form of the data object.
     * @throws EntityAlreadyExistException if another entity with the same alternative key/id exist in the database
     */
    E addWith(D data, Handle handle);

    /**
     * syncs the modifications on this entity to database using the given handle
     *
     * @param entity the entity to be synced
     * @param handle the ahndle to use to execute the update command
     * @throws EntityDoesNotExistException if given entity does not exist in the database
     * @throws EntityUpdateException       when the given entity's alternative key matches with another entity's alternative key in database.
     */
    void updateWith(E entity, Handle handle);
}
