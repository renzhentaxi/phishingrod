package com.phishingrod.core.exceptions;

import java.text.MessageFormat;

public class UnknownIdValidationException extends ValidationException
{
    private static final String messagePattern = "{0} with {1}: ''{2}'' does not exist.";
    public final String id;
    public final String entity_type;
    public final String id_type;

    public UnknownIdValidationException(String entityType, String idType, Object id)
    {
        super(ValidationType.unknown_id, MessageFormat.format(messagePattern, entityType, idType, id));
        this.id = id.toString();
        this.entity_type = entityType;
        this.id_type = idType;

    }
}
