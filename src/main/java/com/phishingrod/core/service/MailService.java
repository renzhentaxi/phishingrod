package com.phishingrod.core.service;

import com.phishingrod.core.domain.PhishingTarget;
import com.phishingrod.core.domain.Sender;
import com.phishingrod.core.domain.SenderServer;
import com.phishingrod.core.domain.SpoofTarget;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import java.util.Properties;

public class MailService
{
    private MailSender sender;

    public MailService()
    {
        sender = new JavaMailSenderImpl();
    }

    public static Properties defaultProp = getDefaultProp();

    private static Properties getDefaultProp()
    {
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        return properties;
    }

    public JavaMailSenderImpl getMailSender(Sender sender)
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setUsername(sender.getName());
        mailSender.setPassword(sender.getPassword());
        SenderServer server = sender.getServer();
        mailSender.setPort(server.getPort());
        mailSender.setHost(server.getHost());
        Properties properties = getDefaultProp();
        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }

    public void Send(PhishingTarget to, SpoofTarget spoofFrom, Sender sender, String message)
    {
        try
        {
            JavaMailSenderImpl mailSender = getMailSender(sender);
            MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage());
            helper.setText(message, true);
            helper.setTo(to.getEmailAddress());
            helper.setFrom(spoofFrom.getEmailAddress());
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }
}
