package com.phishingrod.tools;


import com.phishingrod.util.IOUtil;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

public class MailDownloader
{
    public static void main(String[] args)
    {
        Properties props = new Properties();
        props.put("mail.imap.host", "imap.gmail.com");
        props.put("mail.store.protocol", "imap");
        props.put("mail.imap.ssl.enable", true);
        props.put("mail.imap.port", 993);

        Session session = Session.getInstance(props);

        try (Store store = session.getStore())
        {
            store.connect("phishingrod123@gmail.com","2132018fish");
            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            Message[] messages = inbox.getMessages();
            System.out.println("Number of mails: " + messages.length);
            for (Message message : messages)
            {
                System.out.println("----------------------------------------------");
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Read: " + message.getFlags().contains(Flags.Flag.SEEN));
                ;

                if (message.getContent() instanceof MimeMultipart)
                {
                    MimeMultipart part = (MimeMultipart) message.getContent();
                    System.out.println("Part count: " + part.getCount());
                    for (int i = 0; i < part.getCount(); i++)
                    {
                        System.out.println("\nPart #" + (i + 1));
                        BodyPart bodyPart = part.getBodyPart(i);
                        System.out.println(bodyPart.getContentType());
                        System.out.println(bodyPart.getContent());
                        IOUtil.SaveEmail("inbox/" + message.hashCode() + "_" + i, bodyPart.getContent().toString());
                    }
                }
            }
            inbox.close(true);
        } catch (MessagingException | IOException exception)
        {
            exception.printStackTrace();
        }
    }
}
