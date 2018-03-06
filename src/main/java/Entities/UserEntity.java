package Entities;


import Entities.Data.User;

public class UserEntity extends User implements IUserEntity
{

    private final int id;

    public UserEntity(int id, String firstName, String lastName, String nickName, String emailAddress)
    {
        super(firstName, lastName, nickName, emailAddress);
        this.id = id;
    }

    public UserEntity(int id, User data)
    {
        this(id, data.getFirstName(), data.getLastName(), data.getNickName(), data.getEmailAddress());
    }

    @Override
    public int getId()
    {
        return id;
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
}
