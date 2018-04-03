package com.phishingrod.util;

import org.apache.commons.lang3.builder.StandardToStringStyle;

import java.util.Collection;

public class NoCollectionStyle extends StandardToStringStyle
{
    public NoCollectionStyle()
    {
        setFieldSeparator("; ");
    }


    @Override
    protected void appendClassName(StringBuffer buffer, Object object)
    {
        buffer.append(object.getClass().getSimpleName());
    }

    @Override
    protected void appendDetail(StringBuffer buffer, String fieldName, Collection<?> coll)
    {
        buffer.append("{size: ").append(coll.size()).append("; content: skipped}");
    }

    @Override
    protected void appendIdentityHashCode(StringBuffer buffer, Object object)
    {

    }
}
