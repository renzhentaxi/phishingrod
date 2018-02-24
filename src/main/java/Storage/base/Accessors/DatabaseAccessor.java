package Storage.base.Accessors;

public interface DatabaseAccessor<T>
{
    T get(String identifier);

    void add(T t);

    void update(T t);
}