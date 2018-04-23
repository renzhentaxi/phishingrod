package com.phishingrod.repositories;

import com.phishingrod.domain.EmailTemplateOld;
import org.springframework.data.repository.CrudRepository;

public interface EmailTemplateRepository extends CrudRepository<EmailTemplateOld, Long>
{
}
