package Entities.Senders;

public class SenderEntity extends Sender implements ISenderEntity
{

    private final int id;

    public SenderEntity(int id, String sessionTypeName, String password, String firstName, String lastName, String nickName, String emailAddress)
    {
        super(sessionTypeName, password, firstName, lastName, nickName, emailAddress);
        this.id = id;
    }

    public SenderEntity(int id, ISender sender)
    {
        this(id, sender.getSessionTypeName(), sender.getPassword(), sender.getFirstName(), sender.getLastName(), sender.getNickName(), sender.getEmailAddress());
    }

    @Override
    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public void setSessionTypeName(String sessionTypeName)
    {
        this.sessionTypeName = sessionTypeName;
    }

    @Override
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    @Override
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    @Override
    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    @Override
    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof ISenderEntity && equals((ISenderEntity) obj);
    }

    @Override
    public boolean equals(ISenderEntity other)
    {
        return this.id == other.getId() && super.equals((ISender) other);
    }
}
