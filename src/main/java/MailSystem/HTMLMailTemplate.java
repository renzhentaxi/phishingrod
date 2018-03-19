package MailSystem;

import Entities.Senders.ISender;
import Entities.Users.IUser;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class HTMLMailTemplate implements IMailTemplate
{
    private String _data;

    public HTMLMailTemplate(String data)
    {
        _data = data;
    }

    @Override
    public Message generateMail(ISender from, IUser to)
    {
        Session session = Sessions.GetSession(from.getSessionTypeName());
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
