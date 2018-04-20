package com.phishingrod.api.phishingTarget;

import java.util.Map;

public class ValidationException extends RuntimeException
{
    public Map<String, String> errors;

    public ValidationException(Map<String, String> errors)
    {
        this.errors = errors;
    }
}
