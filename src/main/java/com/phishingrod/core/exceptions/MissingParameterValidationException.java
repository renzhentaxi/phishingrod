package com.phishingrod.core.exceptions;

import java.text.MessageFormat;

public class MissingParameterValidationException extends ValidationException
{
    private static final String messagePattern = "{1}({0}) is missing the required parameter: {2}";

    public MissingParameterValidationException(String type, String containerId, String missingParameter)
    {
        super(ValidationType.invalid_spoofTarget, MessageFormat.format(messagePattern, type, containerId, missingParameter));
    }
}
