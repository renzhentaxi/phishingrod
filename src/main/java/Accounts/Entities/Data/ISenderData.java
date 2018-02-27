package Accounts.Entities.Data;

import Accounts.Entities.User;

public interface ISenderData extends IEntityData
{
    User getUser();

    String getPassword();

    String getSessionType();

}
