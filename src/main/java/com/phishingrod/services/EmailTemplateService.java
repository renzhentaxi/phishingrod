package com.phishingrod.services;

import com.phishingrod.domain.EmailTemplateOld;
import com.phishingrod.repositories.EmailTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateService
{
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    public EmailTemplateService(EmailTemplateRepository emailTemplateRepository)
    {
        this.emailTemplateRepository = emailTemplateRepository;
    }

    public EmailTemplateOld saveTemplate(EmailTemplateOld template)
    {
        return emailTemplateRepository.save(template);
    }

    public EmailTemplateOld getTemplate(long id)
    {
        return emailTemplateRepository.findById(id).orElse(null);
    }

    public Iterable<EmailTemplateOld> getTemplates()
    {
        return emailTemplateRepository.findAll();
    }
}
