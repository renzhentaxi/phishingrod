package MailSystem;

import Accounts.Receiver;
import Accounts.Sender;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

public class MailSender implements IMailSender
{

    @Override
    public void Send(IMail mail, Sender from, Receiver to)
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

    private void Send(Message message, Sender sender, Receiver to, Transport transport)
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
