package Accounts;

import MailSystem.Sessions;

import javax.mail.Session;

public class Sender extends User
{
    private final String _password;
    private final Session _session;

    public Sender(String name, String address, String password, String sessionType)
    {
        super(name, address);
        _password = password;
        _session = Sessions.GetSession(sessionType);
    }

    public String getPassword()
    {
        return _password;
    }


    public Session getSession()
    {
        return _session;
    }
}
