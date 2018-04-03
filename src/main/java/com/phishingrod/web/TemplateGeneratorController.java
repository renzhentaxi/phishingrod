package com.phishingrod.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateGeneratorController
{
    @GetMapping("/template-generator")
    public String templateGenerator()
    {
        return "template-generator";
    }

}
