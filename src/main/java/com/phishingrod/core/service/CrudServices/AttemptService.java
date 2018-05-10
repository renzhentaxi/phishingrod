package com.phishingrod.core.service.CrudServices;

import com.phishingrod.core.domain.Attempt;
import com.phishingrod.core.domain.PhishingTarget;
import com.phishingrod.core.domain.SpoofTarget;
import com.phishingrod.core.domain.Stage;
import com.phishingrod.core.repository.AttemptRepository;
import com.phishingrod.core.service.MailService;
import com.phishingrod.core.service.base.BasicEntityService;
import com.phishingrod.core.service.htmlParser.HtmlParser;
import com.phishingrod.core.service.htmlParser.TemplateConfig;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AttemptService extends BasicEntityService<Attempt, AttemptRepository>
{
    private ParameterService parameterService;

    @Autowired
    public AttemptService(AttemptRepository repository, ParameterService parameterService)
    {
        super(repository, "Attempt");
        this.parameterService = parameterService;
    }


    public Attempt schedule(Attempt attempt)
    {
        attempt.setStage(Stage.scheduled);
        return add(attempt);
    }

    public String execute(long id)
    {
        Attempt attempt = find(id);
        if (attempt.getStage() != Stage.scheduled) return "already sent";

        SpoofTarget spoofTarget = parameterService.syncUsingSet(attempt.getSpoofTarget());
        PhishingTarget phishingTarget = parameterService.syncUsingSet(attempt.getPhishingTarget());

        HtmlParser parser = new HtmlParser(TemplateConfig.DEFAULT_CONFIG);
        Document document = parser.parseParams(attempt.getTemplate(), spoofTarget, phishingTarget);
        String parsedMessage = document.toString();

        document = parser.replaceLinkWithTrackLink(document, id);
        document = parser.installOpenDetector(document, id);
        //send email
        MailService mailer = new MailService();
        mailer.Send(phishingTarget, spoofTarget, attempt.getSender(), document.toString());
        attempt.setSendOn(new Date());
        attempt.setStage(Stage.sent);
        modify(attempt);
        return parsedMessage;
    }

    /**
     * marks the attempt as opened.
     * records the time it was opened as well
     *
     * @param id the id of the attempt
     */
    public void open(long id)
    {
        Attempt attempt = find(id);
        if (attempt.getStage() == Stage.sent)
        {
            attempt.setOpenedOn(new Date());
            attempt.setStage(Stage.opened);
            modify(attempt);
        }
    }

    /**
     * marks the attempt as tricked
     * records the time it was tricked as well
     *
     * @param id the id of the attempt
     */
    public void track(long id)
    {
        Attempt attempt = find(id);
        if (attempt.getStage() != Stage.tricked)
        {
            attempt.setTrickedOn(new Date());
            attempt.setStage(Stage.tricked);
            modify(attempt);
        }
    }
}
