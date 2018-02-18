import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Main
{
    public static void main(String[] args)
    {

        Login login = Login.LoadLogin();
        TextLoader loader = new TextLoader();
        String destMailId = "phishingrod123@gmail.com";

        String smtphost = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtphost);
        props.put("mail.smtp.port", "587");

        Session sessionobj = Session.getInstance(props);
        System.out.println(sessionobj.getProperty("mail.transport.protocol"));
        try
        {
            Message message = new MimeMessage(sessionobj);
            message.setFrom(new InternetAddress("tashi@gmail.com", login.PreferredName));

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(destMailId));

            message.setSubject("hi");
            message.setContent(loader.LoadEmail("testmail"), "text/html");
//            Transport.send(message, message.getAllRecipients(), login.EmailAddress, login.Password);
            System.out.println("success!!");
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

}
