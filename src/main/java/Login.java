import java.io.BufferedReader;
import java.io.FileReader;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/***
 * Simple class to load login info for testing
 */
public class Login extends Authenticator {

    public final String EmailAddress;
    public final String Password;
    public final String PreferredName;

    public Login(String emailAddress, String password, String preferredName) {
        EmailAddress = emailAddress;
        Password = password;
        PreferredName = preferredName;

    }

    public static Login LoadLogin() {
        String userName;
        String password;
        String preferredName;

        try (BufferedReader reader = new BufferedReader(new FileReader("./Data/Login.txt"))) {
            String str = reader.readLine();

            String[] values = str.split(" ");
            preferredName = values[0];
            userName = values[1];
            password = values[2];
            return new Login(userName, password, preferredName);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        throw new RuntimeException("Cant find Login.txt in Data folder");
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(EmailAddress, Password);
    }

}
