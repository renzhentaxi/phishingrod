package Storage.base.Accessors;

import Storage.base.Accessors.Exceptions.EntityAlreadyExistException;
import Storage.base.Accessors.Exceptions.EntityDoesNotExistException;
import Storage.base.Accessors.Exceptions.EntityUpdateException;

public interface IAccessor<E, D>
{
    /**
     * returns the entity with the given id
     *
     * @param id the id of the entity
     * @return the entity if it exist
     * @throws EntityDoesNotExistException if no entity with the id exist
     */
    E get(int id);

    /**
     * converts the data object into entity and adds it to the database
     *
     * @param data the data object to be added to the database
     * @return the entity version of the data object
     * @throws EntityAlreadyExistException if an entity with the same alternative key already exist in the database
     */
    E add(D data);

    /**
     * syncs the database with the given entity
     *
     * @param entity the modified entity to be synced
     * @throws EntityDoesNotExistException if no entity with the id exist
     * @throws EntityUpdateException if an entity with the same alternative key already exist in the database
     */
    void update(E entity);
}

