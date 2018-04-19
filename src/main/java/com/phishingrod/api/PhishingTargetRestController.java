package com.phishingrod.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.services.PhishingTargetService;
import com.phishingrod.util.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.phishingrod.api.responses.PhishingTargetResponseProvider.*;
import static com.phishingrod.util.JsonUtil.populateMapUsingJson;

@RestController
@RequestMapping("/api/phishingTarget")
public class PhishingTargetRestController
{
    private PhishingTargetService service;

    @Autowired
    public PhishingTargetRestController(PhishingTargetService service)
    {
        this.service = service;
    }

    @PostMapping("add")
    public ResponseEntity<JsonNode> add(@RequestBody JsonNode targetJson)
    {

        if (!targetJson.has("emailAddress")) return MISSING_EMAIL_ERROR_RESPONSE;
        String emailAddress = targetJson.get("emailAddress").asText();
        if (service.get(emailAddress).isPresent()) return CONFLICTING_EMAIL_ERROR_RESPONSE;


        PhishingTarget target = new PhishingTarget(emailAddress);

        if (targetJson.has("parameters"))
        {
            JsonNode jsonParameterMap = targetJson.get("parameters");
            Map<String, String> parameterMap = target.getParameterMap();
            populateMapUsingJson(parameterMap, jsonParameterMap);
        }
        service.add(target);

        return addResponse(target);
    }

    @PostMapping("del")
    public ResponseEntity delete(@RequestParam("id") long id)
    {
        return service.delete(id) ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping("mod")
    public ResponseEntity<PhishingTarget> modify(@RequestParam("id") long id, @RequestParam("parameters") String parameters)
    {
        PhishingTarget target = service.get(id);
        if (target != null)
        {
            Map<String, String> paramMap = JsonHelper.parse(parameters);
            paramMap.forEach((key, value) -> System.out.printf("%s : %s\n", key, value));
            //todo not finished yet
            service.modify(target);
            return new ResponseEntity<>(target, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("get")
    public ResponseEntity<JsonNode> get(@RequestParam("id") long id)
    {
        PhishingTarget target = service.get(id);
        return target != null ? getResponse(target) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("all")
    public Iterable<PhishingTarget> all()
    {
        return service.getAll();
    }

}