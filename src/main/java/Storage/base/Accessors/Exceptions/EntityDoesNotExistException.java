package Storage.base.Accessors.Exceptions;

public class EntityDoesNotExistException extends RuntimeException {
    public EntityDoesNotExistException()
    {
        super();
    }

    public EntityDoesNotExistException(String message)
    {
        super(message);
    }

    public EntityDoesNotExistException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
