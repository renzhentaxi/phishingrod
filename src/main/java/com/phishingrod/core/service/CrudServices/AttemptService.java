package com.phishingrod.core.service.CrudServices;

import com.phishingrod.core.domain.*;
import com.phishingrod.core.repository.AttemptRepository;
import com.phishingrod.core.service.base.BasicEntityService;
import com.phishingrod.core.service.htmlParser.HtmlParser;
import com.phishingrod.core.service.htmlParser.TemplateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AttemptService extends BasicEntityService<Attempt, AttemptRepository>
{
    private SenderService senderService;
    private SpoofTargetService spoofTargetService;
    private EmailTemplateService emailTemplateService;
    private PhishingTargetService phishingTargetService;

    @Autowired
    public AttemptService(AttemptRepository repository, SenderService senderService, SpoofTargetService spoofTargetService, EmailTemplateService emailTemplateService, PhishingTargetService phishingTargetService)
    {
        super(repository, "Attempt");
        this.senderService = senderService;
        this.spoofTargetService = spoofTargetService;
        this.emailTemplateService = emailTemplateService;
        this.phishingTargetService = phishingTargetService;
    }

//    public Attempt generateRandom()
//    {
//        EmailTemplate emailTemplate = emailTemplateService.getRandom();
//        List<PhishingTarget> possiblePhishingTarget = emailTemplateService.getPhishingTargetCandidates(emailTemplate);
//        List<SpoofTarget> possibleSpoofTargets = emailTemplateService.getSpoofTargetCandidates(emailTemplate);
//
//        PhishingTarget phishingTarget = pickRandom(possiblePhishingTarget);
//        SpoofTarget spoofTarget = pickRandom(possibleSpoofTargets);
//        Sender sender = senderService.getRandom();
//        return new Attempt(emailTemplate, phishingTarget, spoofTarget, sender);
//    }

    private <E> E pickRandom(List<E> list)
    {
        if (list.size() == 0) return null;
        return list.get((int) (Math.random() * list.size() - 1));
    }

    public void execute(Attempt attempt)
    {
        HtmlParser parser = new HtmlParser(TemplateConfig.DEFAULT_CONFIG);
        String parsedMessage = parser.parse(attempt.getTemplate(), attempt.getSpoofTarget(), attempt.getPhishingTarget());
        System.out.println(parsedMessage);
    }

    public void open(Attempt attempt)
    {
        attempt.setOpenedOn(new Date());
        modify(attempt);
    }

    public void trick(Attempt attempt)
    {
        attempt.setTrickedOn(new Date());
        modify(attempt);
    }
}
