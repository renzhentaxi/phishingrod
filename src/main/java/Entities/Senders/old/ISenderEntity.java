package Entities.Senders.old;

import Entities.IEntity;

public interface ISenderEntity extends IEntity, ISender
{
    void setPassword(String password);

    void setSessionType(String sessionType);
}
