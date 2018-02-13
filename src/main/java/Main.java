import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Login login = Login.LoadLogin();
        TextLoader loader = new TextLoader();
        System.out.println(loader.Load("Mails/testmail.html"));
        String destMailId = "phishingrod123@gmail.com";

        String smtphost = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtphost);
        props.put("mail.smtp.port", "25");

        Session sessionobj = Session.getInstance(props, login);

        try
        {
            Message message = new MimeMessage(sessionobj);
            message.setFrom(new InternetAddress(login.EmailAddress, login.PreferredName));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(destMailId));

            message.setSubject("hi");
            message.setContent(loader.Load("/Mails/testmail.html"), "text/html");
            Transport.send(message);
            System.out.println("success!!");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

}
