package com.phishingrod.api;

import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.services.PhishingTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public boolean add(@RequestParam("emailAddress") String emailAddress)
    {
        return service.add(new PhishingTarget(emailAddress));
    }

    @PostMapping("del")
    public boolean delete(@RequestParam("id") long id)
    {
        return service.delete(id);
    }

    @GetMapping("get")
    public ResponseEntity<PhishingTarget> get(@RequestParam("id") long id)
    {
        Optional<PhishingTarget> target = service.get(id);
        return target.map(phishingTarget -> new ResponseEntity<>(phishingTarget, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("all")
    public Iterable<PhishingTarget> all()
    {
        return service.getAll();
    }
}