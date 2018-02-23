import java.io.BufferedReader;
import java.io.FileReader;

public class TextLoader
{
    private static final String DEFAULT_DATA_PATH = "./data/";
    private static final String DEFAULT_MAILS_PATH = "Mails/";

    //prevent initialization
    private TextLoader()
    {
        throw new UnsupportedOperationException();
    }

    private static String Load(String path)
    {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(DEFAULT_DATA_PATH + path)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append("\n");
            }

        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return sb.toString();
    }

    public static String LoadEmail(String mailName)
    {
        return Load(DEFAULT_MAILS_PATH + mailName + ".html");
    }
}
