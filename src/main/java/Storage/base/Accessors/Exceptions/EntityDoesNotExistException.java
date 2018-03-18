package Storage.base.Accessors.Exceptions;

public class EntityDoesNotExistException extends RuntimeException {

    public EntityDoesNotExistException(String entityType, String identifierName, String identifierValue)
    {
        super("\nNo " + entityType + " with the " + identifierName + " " + identifierValue + " exist in the database.");
    }

    public EntityDoesNotExistException(String entityType, String identifierName, int identifierValue)
    {
        this(entityType, identifierName, Integer.toString(identifierValue));
    }
}
