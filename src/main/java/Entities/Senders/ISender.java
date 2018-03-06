package Entities.Senders;

import Entities.Users.UserEntity;

public interface ISender
{
    UserEntity getUser();

    String getPassword();

    String getSessionType();

}
