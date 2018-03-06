package Entities.Data;

import Entities.UserEntity;

public interface ISender
{
    UserEntity getUser();

    String getPassword();

    String getSessionType();

}
