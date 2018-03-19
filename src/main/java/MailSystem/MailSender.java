package MailSystem;

import Entities.Senders.ISender;
import Entities.Users.IUser;

import javax.mail.*;
import javax.mail.internet.InternetAddress;

public class MailSender implements IMailSender
{

    @Override
    public void Send(IMail mail, ISender from, IUser to)
    {
        Message message = mail.getMessage(from, to);
        Session session = message.getSession();

        Transport transport;
        try
        {
            transport = session.getTransport();
            Send(message, from, to, transport);

        } catch (MessagingException exception)
        {
            exception.printStackTrace();
        }

    }

    private void Send(Message message, ISender sender, IUser to, Transport transport)
    {
        try
        {
            transport.connect(sender.getEmailAddress(), sender.getPassword());
            transport.sendMessage(message, new Address[]{new InternetAddress(to.getEmailAddress())});
        } catch (MessagingException exception)
        {
            exception.printStackTrace();
        }
    }
}
