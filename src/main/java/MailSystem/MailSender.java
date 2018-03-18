package MailSystem;

import Entities.Senders.old.SenderEntity;
import Entities.Users.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;

public class MailSender implements IMailSender
{

    @Override
    public void Send(IMail mail, SenderEntity from, User to)
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

    private void Send(Message message, SenderEntity sender, User to, Transport transport)
    {
        try
        {
            transport.connect(sender.getUser().getEmailAddress(), sender.getPassword());
            transport.sendMessage(message, new Address[]{new InternetAddress(to.getEmailAddress())});
        } catch (MessagingException exception)
        {
            exception.printStackTrace();
        }
    }
}
