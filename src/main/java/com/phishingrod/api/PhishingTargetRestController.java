package com.phishingrod.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.services.PhishingTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.phishingrod.api.responses.PhishingTargetResponseProvider.*;
import static com.phishingrod.util.JsonUtil.mapFromJson;
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

        return responseForAdd(target);
    }

    @PostMapping("del")
    public ResponseEntity delete(@RequestParam("id") long id)
    {
        return service.delete(id) ? new ResponseEntity(HttpStatus.OK) : INVALID_ID_ERROR_RESPONSE;
    }

    @PostMapping("mod")
    public ResponseEntity<JsonNode> modify(@RequestParam("id") long id, @RequestBody JsonNode modDetail)
    {
        PhishingTarget target = service.get(id);

        if (target != null)
        {
            boolean changed = false;
            if (modDetail.has("emailAddress"))
            {
                target.setEmailAddress(modDetail.get("emailAddress").asText());
                changed = true;
            }
            if (modDetail.has("parameters"))
            {
                changed = true;
                Map<String, String> map = mapFromJson(modDetail.get("parameters"));
                target.setParameterMap(map);
            }
            if (changed)
            {
                service.modify(target);
                return responseForModify(target);
            } else
            {
                return EMPTY_MOD_DETAIL_ERROR_RESPONSE;
            }
        }
        return INVALID_ID_ERROR_RESPONSE;
    }

    @GetMapping("get")
    public ResponseEntity<JsonNode> get(@RequestParam("id") long id)
    {
        PhishingTarget target = service.get(id);
        return target != null ? responseForGet(target) : INVALID_ID_ERROR_RESPONSE;
    }

    @GetMapping("all")
    public Iterable<PhishingTarget> all()
    {
        return service.getAll();
    }

}