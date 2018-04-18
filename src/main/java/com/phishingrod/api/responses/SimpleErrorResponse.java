package com.phishingrod.api.responses;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SimpleErrorResponse
{
    private final static JsonNodeFactory jsonFactory = JsonNodeFactory.instance;

    public static ResponseEntity<JsonNode> generateErrorResponse(HttpStatus status, String message)
    {
        ObjectNode messageNode = jsonFactory.objectNode();
        messageNode.put("Error", message);

        return new ResponseEntity<>(messageNode, status);
    }
}
