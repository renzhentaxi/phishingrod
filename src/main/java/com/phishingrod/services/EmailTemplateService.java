package com.phishingrod.services;

import com.phishingrod.domain.EmailTemplate;
import com.phishingrod.repositories.EmailTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class EmailTemplateService
{
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    public EmailTemplateService(EmailTemplateRepository emailTemplateRepository)
    {
        this.emailTemplateRepository = emailTemplateRepository;
    }

    public EmailTemplate saveTemplate(EmailTemplate template)
    {
        return emailTemplateRepository.save(template);
    }

    public EmailTemplate getTemplate(long id)
    {
        return emailTemplateRepository.findById(id).orElse(null);
    }
}
