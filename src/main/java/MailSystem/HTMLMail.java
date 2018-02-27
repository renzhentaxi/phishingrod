package MailSystem;

import Entities.Data.UserData;
import Entities.Sender;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class HTMLMail implements IMail
{
    private String _data;

    public HTMLMail(String data)
    {
        _data = data;
    }

    @Override
    public Message getMessage(Sender from, UserData to)
    {
        Session session = Sessions.GetSession(from.getSessionType());
        Message message = new MimeMessage(session);
        try
        {
//            message.setFrom(from.getInternetAddress());
            message.setContent(_data, "text/html");
        } catch (MessagingException exception)
        {
            exception.printStackTrace();
        }
        return message;
    }


}
