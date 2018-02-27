package Entities;

import Entities.Data.IUserData;

public interface IUser extends IUserData, IEntity
{
    void setFirstName(String firstName);

    void setLastName(String lastName);

    void setNickName(String nickName);

    void setEmailAddress(String emailAddress);
}
