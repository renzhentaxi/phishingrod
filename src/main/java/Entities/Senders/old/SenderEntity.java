package Entities.Senders.old;


import Entities.Users.UserEntity;

public class SenderEntity extends Sender implements ISenderEntity
{
    private final int id;

    public SenderEntity(Sender data)
    {
        this(data.getUser(), data.getPassword(), data.getSessionType());
    }

    public SenderEntity(UserEntity user, String password, String sessionType)
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
