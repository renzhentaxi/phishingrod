package com.phishingrod.emailTemplate;

import com.phishingrod.domain.parameters.EntityParameter;
import com.phishingrod.domain.parameters.Parameter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"entity", "parameter"})
)
@NoArgsConstructor
public class EmailTemplateParameter extends EntityParameter<EmailTemplate>
{
    public EmailTemplateParameter(EmailTemplate entity, Parameter parameter, String value)
    {
        super(entity, parameter, value);
    }

    public EmailTemplateParameter(EmailTemplate entity, Parameter parameter)
    {
        super(entity, parameter);
    }
}
