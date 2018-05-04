package com.phishingrod.core.exceptions;

import java.text.MessageFormat;

public class MissingRequiredValidationException extends ValidationException
{
    public static String messagePattern = "{0} is missing the following field: {1}";
    public final String missing_field;
    public final String entity_type;

    public MissingRequiredValidationException(String entityType, String missingField)
    {
        super(ValidationType.missing_required, MessageFormat.format(messagePattern, entityType, missingField));
        entity_type = entityType;
        missing_field = missingField;
    }
}
