package Entities.Data;

public class UserData implements IUserData
{
    protected String firstName;
    protected String lastName;
    protected String nickName;
    protected String emailAddress;

    public UserData(String firstName, String lastName, String nickName, String emailAddress)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.emailAddress = emailAddress;
    }

    @Override
    public String getFirstName()
    {
        return firstName;
    }


    @Override
    public String getLastName()
    {
        return lastName;
    }


    @Override
    public String getNickName()
    {
        return nickName;
    }


    @Override
    public String getEmailAddress()
    {
        return emailAddress;
    }
}
