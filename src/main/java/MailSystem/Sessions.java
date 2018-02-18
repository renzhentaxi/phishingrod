package MailSystem;

import javax.mail.Session;
import java.util.HashMap;

public class Sessions
{
    private HashMap<String, Session> _data;
    private static Sessions sessions = new Sessions();

    private Sessions()
    {
        _data = new HashMap<>();
    }

    public static Session GetSession(String name)
    {
        Session session = sessions._data.get(name);
        if (session == null)
            throw new RuntimeException("Cant find session with given name:!!");
        return session;
    }

    public static void AddSession(String name, Session session)
    {
        if (!sessions._data.containsKey(name))
            sessions._data.put(name, session);
        else throw new RuntimeException("Session with the given name already exist");
    }
}
