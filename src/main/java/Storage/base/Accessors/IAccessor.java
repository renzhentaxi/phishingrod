package Storage.base.Accessors;

public interface IAccessor<E, D>
{
    E get(int id);

    E add(D data);

    void update(E entity);
}

