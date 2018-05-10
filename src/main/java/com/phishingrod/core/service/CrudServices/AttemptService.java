package com.phishingrod.core.service.CrudServices;

import com.phishingrod.core.domain.Attempt;
import com.phishingrod.core.domain.Stage;
import com.phishingrod.core.repository.AttemptRepository;
import com.phishingrod.core.service.base.BasicEntityService;
import com.phishingrod.core.service.htmlParser.HtmlParser;
import com.phishingrod.core.service.htmlParser.TemplateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AttemptService extends BasicEntityService<Attempt, AttemptRepository>
{

    @Autowired
    public AttemptService(AttemptRepository repository)
    {
        super(repository, "Attempt");
    }

    public void schedule(Attempt attempt)
    {
    }

    public void execute(long id)
    {
        Attempt attempt = find(id);
        HtmlParser parser = new HtmlParser(TemplateConfig.DEFAULT_CONFIG);
        String parsedMessage = parser.parse(attempt);
        attempt.setStage(Stage.sent);
        System.out.println(parsedMessage);
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
