package Accounts;

import javax.mail.internet.InternetAddress;

public interface IUser
{
    /**
     * returns the Full Name of the user
     *
     * @return full name
     */
    String getName();

    String getAddress();

    String getProperty(String name);

    InternetAddress getInternetAddress();
}
