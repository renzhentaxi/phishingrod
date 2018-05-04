package com.phishingrod.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.phishingrod.core.exceptions.ValidationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationExceptionHandler
{
    private final ObjectMapper mapper;

    private static class IgnoreInheritedIntrospector extends JacksonAnnotationIntrospector
    {
        @Override
        public boolean hasIgnoreMarker(final AnnotatedMember m)
        {
            return m.getDeclaringClass().isAssignableFrom(RuntimeException.class) || super.hasIgnoreMarker(m);
        }
    }

    public ValidationExceptionHandler()
    {
        mapper = new ObjectMapper();
        mapper.setAnnotationIntrospector(new IgnoreInheritedIntrospector());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException exception)
    {
        JsonNode response = mapper.valueToTree(exception);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
