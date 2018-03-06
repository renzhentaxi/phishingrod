package Entities.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class User implements IUser
{
    protected String firstName;
    protected String lastName;
    protected String nickName;
    protected String emailAddress;

    public User(String firstName, String lastName, String nickName, String emailAddress)
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

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof User)
        {
            User other = (User) object;
            return this.emailAddress.equals(other.emailAddress) &&
                    this.firstName.equals(other.firstName) &&
                    this.lastName.equals(other.lastName) &&
                    this.nickName.equals(other.nickName);
        }
        return false;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
