package com.phishingrod.core.exceptions;

import java.text.MessageFormat;

public class DuplicateIdValidationException extends ValidationException
{
    private static final String messagePattern = "{0} with {1}: ''{2}'' already exist";

    public final String entity_type;
    public final String id_type;
    public final String id;

    public DuplicateIdValidationException(Object entityType, String idType, Object id)
    {
        this(entityType.getClass().getSimpleName(), idType, id);
    }

    public DuplicateIdValidationException(String entityType, String idType, Object id)
    {
        super(ValidationType.duplicate_id, MessageFormat.format(messagePattern, entityType, idType, id));
        this.entity_type = entityType;
        this.id_type = idType;
        this.id = id.toString();
    }
}
