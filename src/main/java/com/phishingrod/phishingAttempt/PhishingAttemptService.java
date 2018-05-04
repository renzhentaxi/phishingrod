//package com.phishingrod.phishingAttempt;
//
//
//import com.phishingrod.domain.Sender;
//import com.phishingrod.domain.SenderServer;
//import com.phishingrod.domain.phishingTarget.PhishingTarget;
//import com.phishingrod.emailTemplate.EmailTemplate;
//import com.phishingrod.services.EmailFactory;
//import com.phishingrod.services.SenderService;
//import com.phishingrod.spoofTarget.SpoofTarget;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//public class PhishingAttemptService
//{
//    private PhishingAttemptRepository repository;
//    private EmailFactory factory;
//    private SenderService senderService;
//
//    public PhishingAttemptService(PhishingAttemptRepository repository, EmailFactory factory, SenderService senderService)
//    {
//        this.repository = repository;
//        this.factory = factory;
//        this.senderService = senderService;
//    }
//
//    public PhishingAttempt startAttempt(PhishingAttempt attempt)
//    {
//        String parsedEmail = factory.from(attempt.getTemplate(), attempt.getSpoofTarget(), attempt.getPhishingTarget());
//
//        SenderServer server = new SenderServer("gmail", "smtp.sendgrid.net", true, 587);
//        Sender sender = new Sender(server, "apikey", "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");
//
//        attempt.setDate(new Date());
//        senderService.send(parsedEmail, attempt.getSpoofTarget(), attempt.getPhishingTarget(), sender);
//        return repository.save(attempt);
//    }
//
//}
