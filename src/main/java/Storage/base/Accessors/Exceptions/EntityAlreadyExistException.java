package Storage.base.Accessors.Exceptions;

public class EntityAlreadyExistException extends RuntimeException {

    public EntityAlreadyExistException(String entityType, String identifierName, String identifierValue, Object object)
    {
        super("\nA " + entityType + " with the same " + identifierName + "(" + identifierValue + ") already exist in the database." +
                "\nOffending " + entityType + " :" + object.toString());
    }

    public EntityAlreadyExistException(String entityType, String identifierName, int identifierValue, Object object)
    {
        this(entityType, identifierName, Integer.toString(identifierValue), object);
    }
}
