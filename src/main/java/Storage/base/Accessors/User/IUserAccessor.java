package Storage.base.Accessors.User;

import Entities.Users.User;
import Entities.Users.UserEntity;
import Storage.base.Accessors.Exceptions.EntityDoesNotExistException;
import Storage.base.Accessors.IAccessor;

public interface IUserAccessor extends IAccessor<UserEntity, User>
{

    /**
     * returns the user with the emailAddress
     *
     * @param emailAddress the emailAddress of the user
     * @return the user
     * @throws EntityDoesNotExistException when there is no user in the database with the given email.
     */
    UserEntity getByEmail(String emailAddress);
}