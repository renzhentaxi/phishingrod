package Entities.Users;

import Entities.IEntity;

public interface IUserEntity extends IUser, IEntity
{
    void setFirstName(String firstName);

    void setLastName(String lastName);

    void setNickName(String nickName);

    void setEmailAddress(String emailAddress);
}