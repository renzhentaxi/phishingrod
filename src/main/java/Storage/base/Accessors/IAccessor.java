package Storage.base.Accessors;

import Accounts.Entities.Data.IEntityData;
import Accounts.Entities.IEntity;

public interface IAccessor<E extends IEntity, D extends IEntityData>
{
    E get(int id);

    E add(D data);

    void update(E entity);
}

