package Entities;


import Entities.Data.SenderData;

public class Sender extends SenderData implements ISender
{
    private final int id;

    public Sender(SenderData data)
    {
        this(data.getUser(), data.getPassword(), data.getSessionType());
    }

    public Sender(User user, String password, String sessionType)
    {
        super(user, password, sessionType);
        id = user.getId();
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public void setSessionType(String sessionType)
    {
        this.sessionType = sessionType;
    }
}
