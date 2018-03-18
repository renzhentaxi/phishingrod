package Entities.Senders.old;

import Entities.Users.UserEntity;

public class Sender implements ISender
{
    private final UserEntity user;
    protected String password;
    protected String sessionType;

    public Sender(UserEntity user, String password, String sessionType)
    {
        this.user = user;
        this.password = password;
        this.sessionType = sessionType;
    }


    @Override
    public UserEntity getUser()
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
