package com.phishingrod.api;

public class ValidationException extends RuntimeException
{
    public final String type;
    public final String message;

    public ValidationException(String message, String type)
    {
        this.type = type;
        this.message = message;
    }
}
