package com.phishingrod.api;

import com.phishingrod.api.base.EntityRestController;
import com.phishingrod.core.domain.EmailTemplate;
import com.phishingrod.core.repository.EmailTemplateRepository;
import com.phishingrod.core.service.CrudServices.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emailTemplate")
public class EmailTemplateRestController extends EntityRestController<EmailTemplate, EmailTemplateRepository, EmailTemplateService>
{
    @Autowired
    public EmailTemplateRestController(EmailTemplateService service)
    {
        super(service);
    }
}
