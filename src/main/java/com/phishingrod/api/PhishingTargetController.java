package com.phishingrod.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.services.PhishingTargetService;
import com.phishingrod.util.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/phishingTarget")
public class PhishingTargetController
{
    private PhishingTargetService service;
    private final static JsonNodeFactory factory = JsonNodeFactory.instance;
    private final static JsonNode ADD_EMAIL_CONFLICT_RESPONSE = factory.objectNode().put("error", "A Phishing Target with the same email address already exists on the server");
    private final static JsonNode ADD_EMAIL_REQUIRED_RESPONSE = factory.objectNode().put("error", "Missing required emailAddress field in the json.");
    private final static JsonNode PHISHINGTARGET_NOT_FOUND_RESPONSE = factory.objectNode().put("error", "No phishing target with the id exists on the database");

    @Autowired
    public PhishingTargetController(PhishingTargetService service)
    {
        this.service = service;
    }

    @PostMapping("add")
    public ResponseEntity<JsonNode> add(@RequestBody JsonNode targetJson)
    {
        if (targetJson.has("emailAddress"))
        {
            String emailAddress = targetJson.get("emailAddress").asText();
            if (service.get(emailAddress).isPresent())
            {
                return new ResponseEntity<>(ADD_EMAIL_CONFLICT_RESPONSE, HttpStatus.BAD_REQUEST);
            } else
            {
                PhishingTarget target = new PhishingTarget(emailAddress);
                if (targetJson.has("parameters"))
                {
                    JsonNode parameterMap = targetJson.get("parameters");

                    Iterator<Map.Entry<String, JsonNode>> iterator = parameterMap.fields();
                    while (iterator.hasNext())
                    {
                        Map.Entry<String, JsonNode> field = iterator.next();
                        target.addParameter(field.getKey(), field.getValue().textValue());
                    }
                }
                service.add(target);

                ObjectNode response = factory.objectNode();
                response.put("lastModified", target.getLastModified().toString());
                response.put("createdAt", target.getCreatedAt().toString());
                response.put("id", target.getId());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else
        {
            return new ResponseEntity<>(ADD_EMAIL_REQUIRED_RESPONSE, HttpStatus.BAD_REQUEST);
        }

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