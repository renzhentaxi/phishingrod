package com.phishingrod.api.phishingTarget;

import com.fasterxml.jackson.annotation.JsonView;
import com.phishingrod.api.RestView;
import com.phishingrod.api.phishingTarget.validation.PhishingTargetAddValidator;
import com.phishingrod.api.phishingTarget.validation.PhishingTargetIdValidator;
import com.phishingrod.api.phishingTarget.validation.PhishingTargetModValidator;
import com.phishingrod.domain.next.phishingTarget.PhishingTarget;
import com.phishingrod.services.PhishingTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/phishingTarget")
public class PhishingTargetRestController
{
    private PhishingTargetService service;
    private PhishingTargetAddValidator addValidator;
    private PhishingTargetModValidator modValidator;
    private PhishingTargetIdValidator idValidator;

    @Autowired
    public PhishingTargetRestController(PhishingTargetService service)
    {
        this.service = service;
        this.modValidator = new PhishingTargetModValidator(service);
        this.idValidator = new PhishingTargetIdValidator(service);
        this.addValidator = new PhishingTargetAddValidator(service);
    }

    @PostMapping("add")
    @JsonView(RestView.Add.class)
    public PhishingTarget add(@RequestBody PhishingTarget toAdd)
    {
        addValidator.validate(toAdd);
        return service.add(toAdd);
    }

    @PostMapping("del")
    public ResponseEntity delete(@RequestParam("id") long id)
    {
        idValidator.validate(id);

        service.delete(id);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("mod")
    @JsonView(RestView.Modify.class)
    public PhishingTarget modify(@RequestParam("id") long id, @RequestBody PhishingTarget toModify)
    {
        idValidator.validate(id);
        toModify.setId(id);
        modValidator.validate(toModify);

        return service.modify(toModify);
    }

    @GetMapping("get")
    @JsonView(RestView.Get.class)
    public PhishingTarget get(@RequestParam("id") long id)
    {
        idValidator.validate(id);

        return service.get(id);
    }

    @GetMapping("all")
    @JsonView(RestView.Get.class)
    public List<PhishingTarget> all()
    {
        return service.getAll();
    }

}