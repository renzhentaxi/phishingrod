package com.phishingrod.util;

import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;

public class EntityToStringStyle extends StandardToStringStyle
{
    private static NoCollectionStyle style = new NoCollectionStyle();

    public EntityToStringStyle()
    {
        setContentStart("\n");
        setFieldSeparator("\n");
        setFieldNameValueSeparator(": ");
        setContentEnd("\n");
    }

    protected void appendDetail(StringBuffer buffer, String fieldName, Collection<?> coll)
    {
        int i = 0;
        for (Object object : coll)
        {
            buffer.append("\n\t").append(i++).append(":").append(ToStringBuilder.reflectionToString(object, style));
        }
    }

    @Override
    protected void appendClassName(StringBuffer buffer, Object object)
    {
        buffer.append("===================================================\n");
        buffer.append("type: ").append(object.getClass().getSimpleName());
    }

    @Override
    protected void appendIdentityHashCode(StringBuffer buffer, Object object)
    {

    }
}
