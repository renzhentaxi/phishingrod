package Entities.Senders;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Sender implements ISender
{
    //sender properties
    protected String sessionTypeName;
    protected String password;

    // User properties
    protected String firstName;
    protected String lastName;
    protected String nickName;
    protected String emailAddress;

    public Sender(String sessionTypeName, String password, String firstName, String lastName, String nickName, String emailAddress)
    {
        this.sessionTypeName = sessionTypeName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.emailAddress = emailAddress;
    }

    @Override
    public String getSessionTypeName()
    {
        return sessionTypeName;
    }

    @Override
    public String getPassword()
    {
        return password;
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

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
