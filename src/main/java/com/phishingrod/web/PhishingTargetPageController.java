package com.phishingrod.web;

import com.phishingrod.services.PhishingTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("data/phishingTarget")
public class PhishingTargetPageController
{
    private PhishingTargetService service;

    @Autowired
    public PhishingTargetPageController(PhishingTargetService service)
    {
        this.service = service;
    }

    @GetMapping("all")
    public String getAll(Model model)
    {
        model.addAttribute("targets", service.all());
        return "PhishingTarget/phishingTargets";
    }
}