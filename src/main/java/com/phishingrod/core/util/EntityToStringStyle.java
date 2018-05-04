package com.phishingrod.core.util;

import com.phishingrod.core.domain.base.IEmailKeyEntity;
import com.phishingrod.core.domain.base.IIdKeyEntity;
import com.phishingrod.core.domain.base.INameKeyEntity;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;

public class EntityToStringStyle extends StandardToStringStyle
{
    public final static EntityToStringStyle instance = new EntityToStringStyle();
    private static final String banner = "\n[========================================]\n";


    private EntityToStringStyle()
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

            buffer.append("\n\t").append(i++).append(":");
            buffer.append("[").append(object.getClass().getSimpleName()).append("]");
            if (object instanceof IIdKeyEntity)
            {
                long id = ((IIdKeyEntity) object).getId();
                buffer.append("id=").append(id);
                if (object instanceof INameKeyEntity)
                {
                    INameKeyEntity obj = (INameKeyEntity) object;
                    buffer.append(" ,name=").append(obj.getName());
                } else if (object instanceof IEmailKeyEntity)
                {
                    IEmailKeyEntity obj = (IEmailKeyEntity) object;
                    buffer.append(" ,email=").append(obj.getEmailAddress());
                }
            } else
            {
//                buffer.append(ToStringBuilder.reflectionToString(object));
            }

        }
    }

    @Override
    protected void appendClassName(StringBuffer buffer, Object object)
    {
        buffer.append(banner);
        buffer.append("entityType: ").append(object.getClass().getSimpleName());
    }

    @Override
    public void appendEnd(StringBuffer buffer, Object object)
    {
        buffer.append(banner);
    }
}
