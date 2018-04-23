package com.phishingrod.api.emailTemplate;

import com.phishingrod.domain.EmailTemplateOld;
import com.phishingrod.services.EmailTemplateServiceOld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/mail/upload")

public class TemplateController
{
    private EmailTemplateServiceOld emailService;

    @Autowired
    public TemplateController(EmailTemplateServiceOld emailService)
    {
        this.emailService = emailService;
    }


    @PostMapping()
    public long upload(@RequestParam("name") String name, @RequestParam("email") String email)
    {
        EmailTemplateOld template = new EmailTemplateOld(name, email, email, new Date());
        template = emailService.saveTemplate(template);
        return template.getId();
    }

    @GetMapping(params = {"id"})
    public String get(@RequestParam("id") long id)
    {
        EmailTemplateOld template = emailService.getTemplate(id);
        return template.getSourceHtml();
    }
}
