package Entities.SessionTypes;

import javax.mail.Session;
import java.util.Properties;

public class SessionType implements ISessionType
{
    protected String name;
    protected String host;
    protected int port;
    protected boolean auth;
    protected boolean tls;

    public SessionType(String name, String host, int port, boolean auth, boolean tls)
    {
        this.name = name;
        this.host = host;
        this.port = port;
        this.auth = auth;
        this.tls = tls;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public String getHost()
    {
        return host;
    }

    @Override
    public int getPort()
    {
        return port;
    }

    @Override
    public boolean getAuth()
    {
        return auth;
    }

    @Override
    public boolean getTls()
    {
        return tls;
    }

    @Override
    public Session getSessionInstance()
    {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", tls);
        return Session.getInstance(props);
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof SessionType && equals((SessionType) obj);
    }

    public boolean equals(SessionType other)
    {
        return other.port == this.port &&
                other.auth == this.auth &&
                other.tls == this.tls &&
                other.host.equals(this.host) &&
                other.name.equals(this.name);

    }
}
