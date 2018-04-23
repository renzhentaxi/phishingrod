package com.phishingrod.api.phishingTarget;

import java.util.HashMap;
import java.util.Map;

public abstract class SimpleValidator<T>
{
    public void validate(T target)
    {
        Map<String, String> errors = new HashMap<>();
        validateThis(target, errors);
        if (!errors.isEmpty()) throw new ValidationExceptionOld(errors);
    }

    protected abstract void validateThis(T target, Map<String, String> errors);
}
