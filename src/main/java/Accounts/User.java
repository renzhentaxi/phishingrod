package Accounts;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

public abstract class User implements IUser
{
    private final String _name;
    private final String _address;
    private InternetAddress _internetAddress;

    User(String name, String address)
    {
        _name = name;
        _address = address;
        try
        {
            _internetAddress = new InternetAddress(address, name);
        } catch (UnsupportedEncodingException exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public String getName()
    {
        return _name;
    }

    @Override
    public String getAddress()
    {
        return _address;
    }

    @Override
    public String getProperty(String name)
    {
        throw new NotImplementedException();
    }

    @Override
    public InternetAddress getInternetAddress()
    {
        return _internetAddress;
    }
}
