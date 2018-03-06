package Entities.SessionTypes;

import Entities.IEntity;

public interface ISessionTypeEntity extends IEntity, ISessionType
{
    void setHost(String host);

    void setPort(int port);

    void setAuth(boolean auth);

    void setTls(boolean tls);
}
