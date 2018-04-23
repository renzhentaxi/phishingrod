package com.phishingrod.api.phishingTarget;

import java.util.Map;

public class ValidationExceptionOld extends RuntimeException
{
    public Map<String, String> errors;

    public ValidationExceptionOld(Map<String, String> errors)
    {
        this.errors = errors;
    }
}
