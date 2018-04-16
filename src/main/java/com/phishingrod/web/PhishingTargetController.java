package com.phishingrod.web;

import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.repositories.PhishingTargetRepository;

import com.phishingrod.services.PhishingTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("data/phishingTarget")
public class PhishingTargetController
{
    private PhishingTargetRepository repository;
    private PhishingTargetService service;

    @Autowired
    public PhishingTargetController(PhishingTargetRepository repository, PhishingTargetService service)
    {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("all")
    public String getAll(Model model)
    {
        model.addAttribute("targets", repository.findAll());
        return "PhishingTarget/phishingTargets";
    }

    @PostMapping("all/add")
    @ResponseBody
    public boolean add(@RequestParam("emailAddress") String emailAddress)
    {
        return service.add(new PhishingTarget(emailAddress));
    }

    @PostMapping("all/delete")
    @ResponseBody
    public boolean delete(@RequestParam("id") long id)
    {
        return service.delete(id);
    }
}