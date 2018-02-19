import Accounts.Receiver;
import Accounts.Sender;
import MailSystem.HTMLMail;
import MailSystem.IMail;
import MailSystem.MailSender;
import MailSystem.Sessions;

import javax.mail.Session;
import java.util.Properties;

public class Main
{
    public static void main(String[] args)
    {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props);
        Sessions.AddSession("gmail", session);


        Sender sender = new Sender("taxi", "phishingrod123@gmail.com", "2132018fish", "gmail");
        Receiver receiver = new Receiver("jeff", "phishingrod123@gmail.com");
        Receiver receiver1 = new Receiver("hi", "bptashi@gmail.com");


        IMail mail = new HTMLMail(new TextLoader().LoadEmail("testmail"));

        MailSender mailSender = new MailSender();
        mailSender.Send(mail, sender, receiver);
        mailSender.Send(mail, sender, receiver1);

    }
}
