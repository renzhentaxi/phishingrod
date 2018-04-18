package com.phishingrod.api.responses;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.phishingrod.domain.PhishingTarget;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.phishingrod.api.responses.SimpleErrorResponse.generateErrorResponse;

public class PhishingTargetResponseProvider
{

    public static final ResponseEntity<JsonNode> MISSING_EMAIL_ERROR_RESPONSE = generateErrorResponse(HttpStatus.BAD_REQUEST, "Missing required emailAddress field in the json");
    public static final ResponseEntity<JsonNode> CONFLICTING_EMAIL_ERROR_RESPONSE = generateErrorResponse(HttpStatus.BAD_REQUEST, "A phishing Target with the same email address already exists on the server");
    public static final ResponseEntity<JsonNode> INVALID_ID_ERROR_RESPONSE = generateErrorResponse(HttpStatus.NOT_FOUND, "No phishing target with the id exists on the database");

    private static final JsonNodeFactory jsonFactory = JsonNodeFactory.instance;


    public static ResponseEntity<JsonNode> generateAddResponse(PhishingTarget target)
    {
        ObjectNode response = jsonFactory.objectNode();
        response.put("lastModified", target.getLastModified().toString());
        response.put("createdAt", target.getCreatedAt().toString());
        response.put("id", target.getId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
