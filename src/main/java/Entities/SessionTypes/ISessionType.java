package Entities.SessionTypes;

import javax.mail.Session;

/**
 * a sessionType allows creation of session objects that is needed to send emails.
 */
public interface ISessionType
{
    /**
     * returns the name of the sessionType.
     * the name should be unique.
     * e.g. gmail, hotmail etc
     *
     * @return the name of the sessionType
     */
    String getName();

    /**
     * returns the host location
     * e.g. smtp.gmail.com
     *
     * @return the host location
     */
    String getHost();

    /**
     * returns the port number to be used
     *
     * @return the port number
     */
    int getPort();

    /**
     * returns whether the session supports authentication or not
     *
     * @return true if uses authentication, false if not
     */
    boolean getAuth();

    /**
     * returns whether the session object returned uses tls or not
     *
     * @return true if uses tls, false if not
     */
    boolean getTls();

    /**
     * returns an session object.
     * the values of the session object is based on the values of this object.
     *
     * @return session object
     */
    Session getSessionInstance();
}
