package Entities.Data;

import Entities.User;

public interface ISenderData extends IEntityData
{
    User getUser();

    String getPassword();

    String getSessionType();

}
