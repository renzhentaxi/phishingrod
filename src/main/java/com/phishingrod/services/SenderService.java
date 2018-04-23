package com.phishingrod.services;

import com.phishingrod.domain.Sender;
import com.phishingrod.domain.SenderServer;
import com.phishingrod.domain.SpoofTarget;
import com.phishingrod.domain.phishingTarget.PhishingTarget;
import com.phishingrod.repositories.SenderRepository;
import com.phishingrod.repositories.SenderServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class SenderService
{
    private SenderRepository senderRepository;
    private SenderServerRepository serverRepository;

    private Map<Long, JavaMailSender> senderMap;
    private Map<Long, Session> sessionMap;


    @Autowired
    public SenderService(SenderRepository senderRepository, SenderServerRepository serverRepository)
    {
        this.senderRepository = senderRepository;
        this.serverRepository = serverRepository;

        senderMap = new HashMap<>();
        sessionMap = new HashMap<>();
    }

    //create sender
    //modify sender
    //delete sender
    //find senders by loginName <-- not unique
    //find sender by id


    public void send(String html, SpoofTarget from, PhishingTarget to, Sender sender)
    {
        JavaMailSender mailSender = getMailSender(sender);
        MimeMessage message = configureMessage(mailSender.createMimeMessage(), html, from, to);
        mailSender.send(message);
    }

    private MimeMessage configureMessage(MimeMessage message, String html, SpoofTarget from, PhishingTarget to)
    {
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try
        {
            helper.setFrom(from.getEmailAddress());
            helper.setTo(to.getEmailAddress());
            helper.setText(html, true);
            return helper.getMimeMessage();
        } catch (MessagingException exception)
        {

            throw new RuntimeException(exception);
        }

    }

    private JavaMailSender getMailSender(Sender sender)
    {
        return senderMap.computeIfAbsent(sender.getId(), aLong -> createMailSender(sender));
    }

    private JavaMailSender createMailSender(Sender sender)
    {
        Session session = getSession(sender.getServer());
        JavaMailSenderImpl jSender = new JavaMailSenderImpl();
        jSender.setSession(session);
        jSender.setUsername(sender.getLoginName());
        jSender.setPassword(sender.getPassword());
        return jSender;
    }

    private Session getSession(SenderServer server)
    {
        return sessionMap.computeIfAbsent(server.getId(), (i) -> createSession(server));
    }

    private Session createSession(SenderServer server)
    {
        Properties props = new Properties();
        props.put("mail.smtp.host", server.getHost());
        props.put("mail.smtp.port", server.getPort());
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", server.isTls());

        return Session.getInstance(props);
    }
}
