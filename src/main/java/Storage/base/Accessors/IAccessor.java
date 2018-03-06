package Storage.base.Accessors;

import Entities.IEntity;

public interface IAccessor<E extends IEntity, D>
{
    E get(int id);

    E add(D data);

    void update(E entity);
}

