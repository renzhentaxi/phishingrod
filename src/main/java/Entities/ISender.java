package Entities;

import Entities.Data.ISenderData;

public interface ISender extends IEntity, ISenderData
{
    void setPassword(String password);

    void setSessionType(String sessionType);
}
