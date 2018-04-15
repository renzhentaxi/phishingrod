package com.phishingrod.web;

import com.phishingrod.domain.EmailTemplate;
import com.phishingrod.services.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/mail/upload")

public class MailController
{
    private EmailTemplateService emailService;

    @Autowired
    public MailController(EmailTemplateService emailService)
    {
        this.emailService = emailService;
    }


    @PostMapping()
    @ResponseBody
    public long upload(@RequestParam("name") String name, @RequestParam("email") String email)
    {
        EmailTemplate template = new EmailTemplate(name, email, email, new Date());
        template = emailService.saveTemplate(template);
        return template.getId();
    }

    @GetMapping(params = {"id"})
    @ResponseBody
    public String upload(@RequestParam("id") long id)
    {
        EmailTemplate template = emailService.getTemplate(id);
        return template.toString();
    }

    @GetMapping()
    public String uploader()
    {
        return "uploadHtml";
    }
}
