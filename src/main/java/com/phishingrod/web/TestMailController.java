package com.phishingrod.web;

import com.phishingrod.emailTemplate.EmailTemplate;
import com.phishingrod.emailTemplate.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestMailController
{
    private EmailTemplateService service;

    @Autowired
    public TestMailController(EmailTemplateService service)
    {
        this.service = service;
    }

    @GetMapping("/testHtml")
    @ResponseBody
    public String templateGenerator(@RequestParam("id") long id)
    {
        EmailTemplate t = service.get(id);
        if (t == null) return "no template";
        return t.getSourceHtml();
    }

}
