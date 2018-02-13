import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        String destMailId = "renzhentaxibaerde@mail.adelphi.edu";
        String sendMailId = "phishingrod123@gmail.com";

        String name = "phishingrod123@gmail.com";
        String pwd = "2132018fish";

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
                        return new PasswordAuthentication(name, pwd);
                    }
                }
        );

        try {
            Message message = new MimeMessage(sessionobj);
            message.setFrom(new InternetAddress(sendMailId));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(destMailId));

            message.setSubject("hi");
//            message.setText("hi");
            message.setContent("<h1> hi</h1>" + "<img src=\"https://www.blog.google/static/blog/images/google-200x200.7714256da16f.png>\"", "test/html");
            Transport.send(message);
            System.out.println("success!!");
        } catch (MessagingException exception) {
            exception.printStackTrace();
        }
    }

}
