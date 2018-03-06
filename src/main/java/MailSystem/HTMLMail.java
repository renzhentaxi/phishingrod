package MailSystem;

import Entities.Data.User;
import Entities.SenderEntity;

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
    public Message getMessage(SenderEntity from, User to)
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
