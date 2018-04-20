package com.phishingrod.api.errors;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.phishingrod.api.phishingTarget.validation.ValidationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestErrorHandler
{
    private static final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationExceptionNew(ValidationException exception)
    {
        ObjectNode response = nodeFactory.objectNode();
        ObjectNode errorNodes = nodeFactory.objectNode();

        for (Map.Entry<String, String> entry : exception.errors.entrySet())
        {
            errorNodes.put(entry.getKey(), entry.getValue());
        }
        response.set("Error", errorNodes);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
