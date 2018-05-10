package com.phishingrod.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.phishingrod.core.domain.*;
import com.phishingrod.core.service.CrudServices.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/attempt")
public class AttemptRestController
{
    private AttemptService service;
    private EmailTemplateService emailTemplateService;
    private SpoofTargetService spoofTargetService;
    private SenderService senderService;
    private PhishingTargetService phishingTargetService;

    @Autowired
    public AttemptRestController(AttemptService service, EmailTemplateService emailTemplateService, SpoofTargetService spoofTargetService, SenderService senderService, PhishingTargetService phishingTargetService)
    {
        this.service = service;
        this.emailTemplateService = emailTemplateService;
        this.spoofTargetService = spoofTargetService;
        this.senderService = senderService;
        this.phishingTargetService = phishingTargetService;
    }

    @GetMapping(path = "{id}/open")
    public void open(@PathVariable("id") long id)
    {
        service.open(id);
        System.out.println("Someone has opened the email");
    }

    @GetMapping(path = "{id}/track")
    public ModelAndView click(@PathVariable("id") long id)
    {
        service.track(id);
        return new ModelAndView("trickHtml");
    }

    @PostMapping(path = "{id}/execute")
    public String execute(@PathVariable("id") long id)
    {
        return service.execute(id);
    }

    @PostMapping("schedule")
    public Attempt schedule(@RequestBody JsonNode toAdd)
    {
        EmailTemplate template = emailTemplateService.get(toAdd.get("emailTemplate").asText());
        PhishingTarget phishingTarget = phishingTargetService.get(toAdd.get("phishingTarget").asText());
        SpoofTarget spoofTarget = spoofTargetService.get(toAdd.get("spoofTarget").asText());
        Sender sender = senderService.get(toAdd.get("sender").asText());
        Attempt attempt = new Attempt(template, phishingTarget, spoofTarget, sender);
        return service.schedule(attempt);
    }

    @GetMapping
    public List<Attempt> all()
    {
        return service.all();
    }

    @GetMapping("/{id}")
    public Attempt get(@PathVariable("id") long id)
    {
        return service.get(id);
    }
}
