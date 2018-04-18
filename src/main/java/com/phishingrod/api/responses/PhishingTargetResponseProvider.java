package com.phishingrod.api.responses;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.phishingrod.domain.PhishingTarget;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static com.phishingrod.api.responses.SimpleErrorResponse.generateErrorResponse;
import static com.phishingrod.util.JsonUtil.populateJsonUsingMap;

public class PhishingTargetResponseProvider
{

    public static final ResponseEntity<JsonNode> MISSING_EMAIL_ERROR_RESPONSE = generateErrorResponse(HttpStatus.BAD_REQUEST, "Missing required emailAddress field in the json");
    public static final ResponseEntity<JsonNode> CONFLICTING_EMAIL_ERROR_RESPONSE = generateErrorResponse(HttpStatus.BAD_REQUEST, "A phishing Target with the same email address already exists on the server");
    public static final ResponseEntity<JsonNode> INVALID_ID_ERROR_RESPONSE = generateErrorResponse(HttpStatus.NOT_FOUND, "No phishing target with the id exists on the database");

    private static final JsonNodeFactory jsonFactory = JsonNodeFactory.instance;

    private static final String ID = "id";
    private static final String LAST_MODIFIED = "lastModified";
    private static final String CREATED_AT = "createdAt";
    private static final String EMAIL_ADDRESS = "emailAddress";

    public static ResponseEntity<JsonNode> addResponse(PhishingTarget target)
    {
        ObjectNode response = jsonFactory.objectNode();
        response.put(ID, target.getId());
        response.put(LAST_MODIFIED, target.getLastModified().toString());
        response.put(CREATED_AT, target.getCreatedAt().toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<JsonNode> getResponse(PhishingTarget target)
    {

        ObjectNode response = jsonFactory.objectNode();
        response.put(ID, target.getId());
        response.put(EMAIL_ADDRESS, target.getEmailAddress());
        response.put(LAST_MODIFIED, target.getLastModified().toString());
        response.put(CREATED_AT, target.getCreatedAt().toString());

        ObjectNode parameters = jsonFactory.objectNode();
        Map<String, String> parameterMap = target.getParameterMap();
        populateJsonUsingMap(parameters, parameterMap);

        response.set("parameters", parameters);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
