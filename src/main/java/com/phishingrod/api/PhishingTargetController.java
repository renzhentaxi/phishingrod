package com.phishingrod.api;

import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.services.PhishingTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/phishingTarget")
public class PhishingTargetController
{
    private PhishingTargetService service;

    @Autowired
    public PhishingTargetController(PhishingTargetService service)
    {
        this.service = service;
    }

    @PostMapping("add")
    @ResponseBody
    public boolean add(@RequestParam("emailAddress") String emailAddress)
    {
        return service.add(new PhishingTarget(emailAddress));
    }

    @PostMapping("delete")
    @ResponseBody
    public boolean delete(@RequestParam("id") long id)
    {
        return service.delete(id);
    }
}