import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Login login = Login.LoadLogin();

        String destMailId = "phishingrod123@gmail.com";

        String smtphost = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtphost);
        props.put("mail.smtp.port", "25");

        Session sessionobj = Session.getInstance(
                props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login.EmailAddress, login.Password);
                    }
                }
        );

        try {
            Message message = new MimeMessage(sessionobj);
            message.setFrom(new InternetAddress(login.EmailAddress, login.PreferredName));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(destMailId));

            message.setSubject("hi");
            message.setContent(getMail(), "text/html");
            Transport.send(message);
            System.out.println("success!!");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static String getMail() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("./Data/Mails/testmail.html"))) {
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return sb.toString();
    }
}
