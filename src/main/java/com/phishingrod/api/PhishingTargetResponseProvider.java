package com.phishingrod.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.phishingrod.domain.PhishingTarget;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PhishingTargetResponseProvider
{
    private final JsonNodeFactory jsonFactory = JsonNodeFactory.instance;

    public ResponseEntity<JsonNode> generateErrorResponse(HttpStatus status, String message)
    {
        ObjectNode messageNode = jsonFactory.objectNode();
        messageNode.put("Error", message);

        return new ResponseEntity<>(messageNode, status);
    }

    public ResponseEntity<JsonNode> generateResponseForAdd(PhishingTarget target)
    {
        ObjectNode response = jsonFactory.objectNode();
        response.put("lastModified", target.getLastModified().toString());
        response.put("createdAt", target.getCreatedAt().toString());
        response.put("id", target.getId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
