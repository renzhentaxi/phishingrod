package com.phishingrod.api.emailTemplate;

import com.phishingrod.emailTemplate.EmailTemplate;
import com.phishingrod.emailTemplate.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        EmailTemplate template = new EmailTemplate(name);
        template.setOriginalHtml(email);
        template.setSourceHtml(email);
        template = emailService.add(template);
        return template.getId();
    }

    @GetMapping(params = {"id"})
    public String get(@RequestParam("id") long id)
    {
        EmailTemplate template = emailService.get(id);
        return template.getSourceHtml();
    }
}
