package com.phishingrod.core.util;


import java.io.*;

public class IOUtil
{
    private static final String DEFAULT_DATA_PATH = "./data/";
    private static final String DEFAULT_MAILS_PATH = "Mails/";
    private static final String DEFAULT_WEBSITE_PATH = "Websites/";

    //prevent initialization
    private IOUtil()
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

    private static void Save(String path, String data)
    {
        File file = new File(DEFAULT_DATA_PATH + path);
        File parent = new File(file.getParent());
        if (!parent.exists())
            parent.mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
        {
            writer.write(data);
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static String LoadEmail(String mailName)
    {
        return Load(DEFAULT_MAILS_PATH + mailName + ".template");
    }

    public static void SaveEmail(String mailName, String data)
    {
        Save(DEFAULT_MAILS_PATH + mailName + ".template", data);
    }

    public static void SaveWebsite(String websiteName, String data)
    {
        Save(DEFAULT_WEBSITE_PATH + websiteName + ".template", data);
    }

    public static String LoadFile(String filePath)
    {
        return Load(filePath);
    }

    public static String LoadStream(InputStream stream)
    {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                builder.append(line).append("\n");
            }
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return builder.toString();
    }

}