package com.phishingrod.core.tools;

import com.phishingrod.core.exceptions.DuplicateIdValidationException;


public class test
{

    public static void main(String[] args)
    {
        throw new DuplicateIdValidationException("Spoof Target", "email address", "taxi@gmail.com");

    }
}
