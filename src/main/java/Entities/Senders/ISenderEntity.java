package Entities.Senders;

import Entities.IEntity;

public interface ISenderEntity extends IEntity, ISender
{
    void setPassword(String password);

    void setSessionType(String sessionType);
}
