package com.phishingrod.web;

import com.phishingrod.domain.EmailTemplateOld;
import com.phishingrod.services.EmailTemplateServiceOld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestMailController
{
    private EmailTemplateServiceOld service;

    @Autowired
    public TestMailController(EmailTemplateServiceOld service)
    {
        this.service = service;
    }

    @GetMapping("/testHtml")
    @ResponseBody
    public String templateGenerator(@RequestParam("id") long id)
    {
        EmailTemplateOld t = service.getTemplate(id);
        if(t == null) return "no template";
        return t.getSourceHtml();
    }

}
