package com.phishingrod.api;

import com.phishingrod.domain.EmailTemplate;
import com.phishingrod.services.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/mail/upload")

public class TemplateController
{
    private EmailTemplateService emailService;

    @Autowired
    public TemplateController(EmailTemplateService emailService)
    {
        this.emailService = emailService;
    }


    @PostMapping()
    public long upload(@RequestParam("name") String name, @RequestParam("email") String email)
    {
        EmailTemplate template = new EmailTemplate(name, email, email, new Date());
        template = emailService.saveTemplate(template);
        return template.getId();
    }

    @GetMapping(params = {"id"})
    public String get(@RequestParam("id") long id)
    {
        EmailTemplate template = emailService.getTemplate(id);
        return template.getSourceHtml();
    }
}
