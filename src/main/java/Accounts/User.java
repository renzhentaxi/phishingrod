package Accounts;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

public class User implements IUser
{
    private final String _firstName;
    private final String _lastName;
    private final String _nickName;
    private final String _fullName;

    private final String _emailAddress;
    private InternetAddress _internetAddress;

    public User(String firstName, String lastName, String nickName, String address)
    {
        _firstName = firstName;
        _lastName = lastName;
        _nickName = nickName;
        _fullName = firstName + " " + lastName;

        _emailAddress = address;
        try
        {
            _internetAddress = new InternetAddress(address, lastName);
        } catch (UnsupportedEncodingException exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public String getFirstName()
    {
        return _firstName;
    }

    @Override
    public String getLastName()
    {
        return _lastName;
    }

    @Override
    public String getNickName()
    {
        return _nickName;
    }

    @Override
    public String getFullName()
    {
        return _fullName;
    }

    @Override
    public String getEmailAddress()
    {
        return _emailAddress;
    }

    @Override
    public InternetAddress getInternetAddress()
    {
        return _internetAddress;
    }

    @Override
    public String getProperty(String name)
    {
        throw new NotImplementedException();
    }

    private String _stringRepresentation;

    @Override
    public String toString()
    {
        if (_stringRepresentation == null)
        {
            _stringRepresentation = "[User] firstName: " + _firstName + ", lastName: " + _lastName + ", nickName: " + _nickName + ", email: " + _emailAddress;
        }
        return _stringRepresentation;
    }
}
