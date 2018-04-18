package com.phishingrod.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.services.JsonService;
import com.phishingrod.services.PhishingTargetService;
import com.phishingrod.util.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static com.phishingrod.api.responses.PhishingTargetResponseProvider.*;

@RestController
@RequestMapping("/api/phishingTarget")
public class PhishingTargetRestController
{
    private PhishingTargetService service;
    private JsonService jsonService;
    
    @Autowired
    public PhishingTargetRestController(PhishingTargetService service, JsonService jsonService)
    {
        this.service = service;
        this.jsonService = jsonService;
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

            jsonService.populateMapUsingJson(parameterMap, jsonParameterMap);
        }
        service.add(target);

        return generateAddResponse(target);
    }

    @PostMapping("del")
    public ResponseEntity delete(@RequestParam("id") long id)
    {
        return service.delete(id) ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping("mod")
    public ResponseEntity<PhishingTarget> modify(@RequestParam("id") long id, @RequestParam("parameters") String parameters)
    {
        Optional<PhishingTarget> target = service.get(id);
        if (target.isPresent())
        {
            PhishingTarget t = target.get();
            Map<String, String> paramMap = JsonHelper.parse(parameters);
            paramMap.forEach((key, value) -> System.out.printf("%s : %s\n", key, value));
            //todo not finished yet
            service.modify(t);
            return new ResponseEntity<>(t, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("get")
    public ResponseEntity<JsonNode> get(@RequestParam("id") long id)
    {
        Optional<PhishingTarget> target = service.get(id);
        return target.map(phishingTarget -> new ResponseEntity<>(toNode(phishingTarget), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("all")
    public Iterable<PhishingTarget> all()
    {
        return service.getAll();
    }

    public JsonNode toNode(PhishingTarget target)
    {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("id", target.getId());
        node.put("emailAddress", target.getEmailAddress());
        node.put("lastModified", target.getLastModified().toString());
        node.put("createdAt", target.getCreatedAt().toString());

        ObjectNode parameters = mapper.createObjectNode();

        for (Map.Entry<String, String> entry : target.getParameterMap().entrySet())
        {
            parameters.put(entry.getKey(), entry.getValue());
        }

        node.set("parameters", parameters);
        return node;
    }
}