import java.io.BufferedReader;
import java.io.FileReader;

public class TextLoader
{
    public static final String DEFAULT_DATA_PATH = "./data/";
    public static final String DEFAULT_MAILS_PATH = "Mails/";

    public String Load(String path)
    {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(DEFAULT_DATA_PATH + path)))
        {
            String line = reader.readLine();
            while (line != null)
            {
                sb.append(line).append("\n");
                line = reader.readLine();
            }

        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return sb.toString();
    }

    public String LoadEmail(String mailName)
    {
        return Load(DEFAULT_MAILS_PATH + mailName + ".html");
    }
}
