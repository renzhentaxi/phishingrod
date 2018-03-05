package Storage.base.Accessors.Exceptions;

public class EntityAlreadyExistException extends RuntimeException {
    public EntityAlreadyExistException()
    {
        super();
    }

    public EntityAlreadyExistException(String message)
    {
        super(message);
    }

    public EntityAlreadyExistException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
