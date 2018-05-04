package com.phishingrod.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.phishingrod.core.exceptions.UnknownIdValidationException;
import org.junit.Test;

import java.io.File;

public class ValidationExceptionSerializationTest
{
    private static class IgnoreInheritedIntrospector extends JacksonAnnotationIntrospector
    {
        @Override
        public boolean hasIgnoreMarker(final AnnotatedMember m)
        {
            boolean ignored = m.getDeclaringClass().isAssignableFrom(RuntimeException.class) || super.hasIgnoreMarker(m);
//            System.out.println(m + " " + ignored);
            if (!ignored)
                System.out.println(m.getMember());
            return ignored;
        }
    }

    @Test
    public void unknownId_serialization() throws Exception
    {
        UnknownIdValidationException unknownIdValidationException = new UnknownIdValidationException("SpoofTarget", "email address", 1);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setAnnotationIntrospector(new IgnoreInheritedIntrospector());
        mapper.writeValue(new File("test.targetJson"), unknownIdValidationException);

    }
}
