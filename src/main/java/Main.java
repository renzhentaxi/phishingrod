import Entities.Senders.old.SenderEntity;
import Entities.Users.User;
import Entities.Users.UserEntity;
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

        //create a sender and a receiver
        //later on all these will be stored in a database
        UserEntity user = new UserEntity(0, "taxi", "wut", "myname", "phishingrod123@gmail.com");
        SenderEntity sender = new SenderEntity(user, "2132018fish", "gmail");
        User receiver = new User("jeff", "myboi", "boi", "phishingrod123@gmail.com");

        //load the email
        IMail mail = new HTMLMail(TextLoader.LoadEmail("Chase/index"));

        //create a MailSender which will send the email
        MailSender mailSender = new MailSender();


        mailSender.Send(mail, sender, receiver);
    }
}
