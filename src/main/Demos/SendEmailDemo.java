import Entities.Senders.ISender;
import Entities.SessionTypes.ISessionType;
import Entities.Users.IUser;
import Entities.Users.User;
import MailSystem.HTMLMailTemplate;
import MailSystem.IMailTemplate;
import MailSystem.MailSender;
import MailSystem.Sessions;
import Storage.StorageManager;
import Storage.base.Accessors.Sender.ISenderAccessor;
import Storage.base.Accessors.SessionType.ISessionTypeAccessor;
import Storage.base.Accessors.User.IUserAccessor;
import Storage.base.IStorageManager;
import Util.TextLoader;

import static Database.DatabaseLocation.databaseLocation;

public class SendEmailDemo
{
    public ISessionTypeAccessor sessionTypeAccessor;
    public ISenderAccessor senderAccessor;
    public IUserAccessor userAccessor;


    public SendEmailDemo(String databaseLocation)
    {
        IStorageManager storageManager = StorageManager.newInstance(databaseLocation);

        sessionTypeAccessor = storageManager.getSessionTypeAccessor();
        senderAccessor = storageManager.getSenderAccessor();
        userAccessor = storageManager.getUserAccessor();
    }

    public void run()
    {
        ISessionType gmailSessionType = sessionTypeAccessor.getByName("gmail");
        Sessions.AddSession(gmailSessionType.getName(), gmailSessionType.getSessionInstance());

        ISender demoSender = senderAccessor.getByEmail("phishingrod123@gmail.com");
        IUser demoReciever = new User("jeff", "myboi", "boi", "phishingrod123@gmail.com");

        //load the email
        IMailTemplate mail = new HTMLMailTemplate(TextLoader.LoadEmail("Chase/index"));

        //create a MailSender which will send the email
        MailSender mailSender = new MailSender();


        mailSender.Send(mail, demoSender, demoReciever);

    }

    public static void main(String[] args)
    {
        SendEmailDemo demo = new SendEmailDemo(databaseLocation);
        demo.run();
    }
}
