package com.phishingrod.api;

import com.phishingrod.domain.Sender;
import com.phishingrod.domain.SenderServer;
import com.phishingrod.domain.phishingTarget.PhishingTarget;
import com.phishingrod.emailTemplate.EmailTemplate;
import com.phishingrod.emailTemplate.EmailTemplateService;
import com.phishingrod.services.EmailFactory;
import com.phishingrod.services.PhishingTargetService;
import com.phishingrod.services.SenderService;
import com.phishingrod.spoofTarget.SpoofTarget;
import com.phishingrod.spoofTarget.SpoofTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/send")
public class SendEmailController
{
    private EmailFactory factory;
    private SpoofTargetService spoofTargetService;
    private EmailTemplateService templateService;
    private PhishingTargetService phishingTargetService;

    private SenderService senderService;

    @Autowired
    public SendEmailController(EmailFactory factory, SpoofTargetService spoofTargetService, EmailTemplateService templateService, PhishingTargetService phishingTargetService, SenderService senderService)
    {
        this.factory = factory;
        this.spoofTargetService = spoofTargetService;
        this.templateService = templateService;
        this.phishingTargetService = phishingTargetService;
        this.senderService = senderService;
    }

    @PostMapping
    public void send(@RequestParam("templateId") long templateId, @RequestParam("spoofTargetId") long spoofTargetId, @RequestParam("phishingTargetId") long phishingTargetId)
    {
        EmailTemplate template = templateService.get(templateId);
        SpoofTarget spoofTarget = spoofTargetService.get(spoofTargetId);
        PhishingTarget phishingTarget = phishingTargetService.get(phishingTargetId);

        String parsedEmail = factory.from(template, spoofTarget, phishingTarget);

        System.out.println(parsedEmail);
        SenderServer server = new SenderServer("gmail", "smtp.gmail.com", true, 587);
        Sender sender = new Sender(server, "phishingrod123@gmail.com", "2132018fish");

        senderService.send(parsedEmail, spoofTarget, phishingTarget, sender);
    }
}

