package Storage.base.Accessors.User;

import Entities.Data.UserData;
import Entities.User;
import Storage.base.Accessors.Exceptions.EntityDoesNotExistException;
import Storage.base.Accessors.IAccessor;

public interface IUserAccessor extends IAccessor<User, UserData>
{

    /**
     * returns the user with the emailAddress
     *
     * @param emailAddress the emailAddress of the user
     * @return the user
     * @throws EntityDoesNotExistException when there is no user in the database with the given email.
     */
    User getByEmail(String emailAddress);


}