package MailSystem;

import Accounts.Sender;
import Accounts.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;

public class MailSender implements IMailSender
{

    @Override
    public void Send(IMail mail, Sender from, User to)
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

    private void Send(Message message, Sender sender, User to, Transport transport)
    {
        try
        {
            transport.connect(sender.getAddress(), sender.getPassword());
            transport.sendMessage(message, new Address[]{new InternetAddress(to.getAddress())});
        } catch (MessagingException exception)
        {
            exception.printStackTrace();
        }
    }
}
