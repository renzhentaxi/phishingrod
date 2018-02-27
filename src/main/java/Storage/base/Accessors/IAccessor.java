package Storage.base.Accessors;

import Entities.Data.IEntityData;
import Entities.IEntity;

public interface IAccessor<E extends IEntity, D extends IEntityData>
{
    E get(int id);

    E add(D data);

    void update(E entity);
}

