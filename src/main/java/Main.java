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
        String destMailId = "han.cho95@gmail.com";

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
            message.setContent("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "\n" +
                    "    <head>\n" +
                    "        <meta charset=\"utf-8\">\n" +
                    "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                    "        <style>\n" +
                    "            .logo{\n" +
                    "            background-image: url(img/chaseLogo.png);\n" +
                    "            width: 150px;\n" +
                    "            height: 42px;\n" +
                    "            }\n" +
                    "            .content{\n" +
                    "                background-image: url(img/chaseContent.png);\n" +
                    "                width: 675px;\n" +
                    "                height: 327px;\n" +
                    "            }\n" +
                    "            .bottom{\n" +
                    "                background-image: url(img/chaseEnding.png);\n" +
                    "                width: 625px;\n" +
                    "                height: 350px;\n" +
                    "                margin-left: 23px;\n" +
                    "            }\n" +
                    "            .date{\n" +
                    "                font-family: Arial, Helvetica, sans-serif;\n" +
                    "                font-size: small;\n" +
                    "            }\n" +
                    "            #firstDate{\n" +
                    "                margin-left: 213px;\n" +
                    "                padding-top: 68px;\n" +
                    "            }\n" +
                    "            #secondDate{\n" +
                    "                padding-top: 75px;\n" +
                    "                margin-left: 44px;\n" +
                    "            }\n" +
                    "            #thirdDate{\n" +
                    "                margin-left: 113px;\n" +
                    "            }\n" +
                    "            .email{\n" +
                    "                font-family: Verdana;\n" +
                    "                font-size: x-small;\n" +
                    "            }\n" +
                    "            #firstEmail{\n" +
                    "                font-weight: bold;\n" +
                    "                padding-top: 59px;\n" +
                    "                margin-left: 165px;\n" +
                    "            }\n" +
                    "        </style>\n" +
                    "\n" +
                    "\n" +
                    "    </head>\n" +
                    "\n" +
                    "    <body>\n" +
                    "        <div class=\"logo\"></div>\n" +
                    "        <div class=\"content\">\n" +
                    "            <div class=\"date\" id=\"firstDate\">02/15/2018.</div>\n" +
                    "            <div class=\"date\" id=\"secondDate\">02/15/2018</div>\n" +
                    "            <div class=\"date\" id=\"thirdDate\">02/15/2018.</div>\n" +
                    "        </div>\n" +
                    "        <div class=\"bottom\">\n" +
                    "            <div class=\"email\" id=\"firstEmail\">hannaj92.hj@gmail.com</div>\n" +
                    "        </div>\n" +
                    "    </body>\n" +
                    "</html> \n", "text/html");

            Transport.send(message);
            System.out.println("success!!");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

}
