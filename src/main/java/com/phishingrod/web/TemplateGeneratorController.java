package com.phishingrod.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TemplateGeneratorController
{
    @GetMapping("/template-generator")
    public String templateGenerator(@RequestParam("id") long id, Model model)
    {
        model.addAttribute("id", id);
        return "template-generator";
    }

}
