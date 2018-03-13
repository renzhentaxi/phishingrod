package Entities.SessionTypes;

public class SessionTypeEntity extends SessionType implements ISessionTypeEntity
{
    private final int id;

    public SessionTypeEntity(int id, String name, String host, int port, boolean auth, boolean tls)
    {
        super(name, host, port, auth, tls);
        this.id = id;
    }

    public SessionTypeEntity(int sessionTypeId, ISessionType sessionType)
    {
        super(sessionType.getName(), sessionType.getHost(), sessionType.getPort(), sessionType.getAuth(), sessionType.getTls());
        id = sessionTypeId;
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }
    @Override
    public void setHost(String host)
    {
        this.host = host;
    }

    @Override
    public void setPort(int port)
    {
        this.port = port;
    }

    @Override
    public void setAuth(boolean auth)
    {
        this.auth = auth;
    }

    @Override
    public void setTls(boolean tls)
    {
        this.tls = tls;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof SessionTypeEntity && equals((SessionTypeEntity) obj);
    }

    public boolean equals(SessionTypeEntity other)
    {
        return other.id == this.id && super.equals(other);
    }
}
