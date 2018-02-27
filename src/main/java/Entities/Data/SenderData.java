package Entities.Data;

import Entities.User;

public class SenderData implements ISenderData
{
    private final User user;
    protected String password;
    protected String sessionType;

    public SenderData(User user, String password, String sessionType)
    {
        this.user = user;
        this.password = password;
        this.sessionType = sessionType;
    }


    @Override
    public User getUser()
    {
        return user;
    }

    @Override
    public String getPassword()
    {
        return password;
    }


    @Override
    public String getSessionType()
    {
        return sessionType;
    }

}
