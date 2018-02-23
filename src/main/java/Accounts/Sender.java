package Accounts;

import MailSystem.Sessions;

import javax.mail.Session;

public class Sender extends User
{
    private final String _password;
    private final Session _session;

    public Sender(String firstName, String lastName, String nickName, String address, String password, String sessionType)
    {
        super(firstName, lastName, nickName, address);
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
