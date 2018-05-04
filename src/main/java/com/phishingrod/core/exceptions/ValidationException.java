package com.phishingrod.core.exceptions;

public class ValidationException extends RuntimeException
{
    public final ValidationType error_type;
    public final String error_message;

    protected ValidationException(ValidationType error_type, String error_message)
    {
        super(error_message);
        this.error_type = error_type;
        this.error_message = error_message;
    }
}
