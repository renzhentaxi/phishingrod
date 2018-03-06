package Entities;

import Entities.Data.ISender;

public interface ISenderEntity extends IEntity, ISender
{
    void setPassword(String password);

    void setSessionType(String sessionType);
}
