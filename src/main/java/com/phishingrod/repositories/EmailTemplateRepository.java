package com.phishingrod.repositories;

import com.phishingrod.domain.EmailTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface EmailTemplateRepository extends CrudRepository<EmailTemplate, Long>
{
}
