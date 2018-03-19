import Entities.Senders.ISender;
import Entities.SessionTypes.ISessionType;
import Entities.Users.IUser;
import Entities.Users.User;
import MailSystem.HTMLMail;
import MailSystem.IMail;
import MailSystem.MailSender;
import MailSystem.Sessions;
import Storage.StorageManager;
import Storage.base.IStorageManager;

import javax.mail.Session;

public class MainDemo
{
    public static void main(String[] args)
    {
        String databaseLocation = System.getProperty("user.dir") + "/data/database/dev.db";

        IStorageManager storageManager = StorageManager.newInstance(databaseLocation);
        ISessionType gmailSessionType = storageManager.getSessionTypeAccessor().getByName("gmail");
        ISender demoSender = storageManager.getSenderAccessor().getByEmail("phishingrod123@gmail.com");
        IUser demoReciever = new User("jeff", "myboi", "boi", "phishingrod123@gmail.com");


        Session gmailSession = gmailSessionType.getSessionInstance();
        Sessions.AddSession("gmail", gmailSession);

        //load the email
        IMail mail = new HTMLMail(TextLoader.LoadEmail("Chase/index"));

        //create a MailSender which will send the email
        MailSender mailSender = new MailSender();


        mailSender.Send(mail, demoSender, demoReciever);
    }
}
