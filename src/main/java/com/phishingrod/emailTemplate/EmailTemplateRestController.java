package com.phishingrod.emailTemplate;

import com.phishingrod.api.EntityRestController;
import com.phishingrod.api.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emailTemplate")
public class EmailTemplateRestController extends EntityRestController<EmailTemplate, EmailTemplateService>
{
    @Autowired
    public EmailTemplateRestController(EmailTemplateService service)
    {
        super(service);
    }

    @Override
    public void validateAdd(EmailTemplate toAdd)
    {
        if (service.exist(toAdd.getName()))
        {
            throw new ValidationException("Conflict", "Template with the name: " + toAdd.getName() + " already exist on the database");
        }
    }

    @Override
    public void validateGet(long id)
    {
        if (!service.exist(id))
        {
            throw new ValidationException("Conflict", "Template With the id: " + id + " does not exist");
        }
    }

    @Override
    public void validateDelete(long id)
    {
        if (!service.exist(id))
            throw new ValidationException("Conflict", "Template With the id: " + id + " does not exist");
    }

    @Override
    public void validateModify(EmailTemplate toModify)
    {
        if (!service.exist(toModify.getId()))
        {
            throw new ValidationException("Conflict", "Template With the id: " + toModify.getId() + " does not exist");
        }
    }
}
