package com.phishingrod.services;

import com.phishingrod.domain.EmailTemplateOld;
import com.phishingrod.repositories.EmailTemplateRepositoryOld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateServiceOld
{
    private EmailTemplateRepositoryOld emailTemplateRepository;

    @Autowired
    public EmailTemplateServiceOld(EmailTemplateRepositoryOld emailTemplateRepository)
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
