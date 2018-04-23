package com.phishingrod.services.entity;

import com.phishingrod.domain.components.EmailKeyedEntity;
import com.phishingrod.domain.components.PhishingRodEntity;
import com.phishingrod.repositories.base.EmailKeyedRepository;

public abstract class EmailKeyedEntityService<E extends PhishingRodEntity & EmailKeyedEntity, R extends EmailKeyedRepository<E>> extends GeneralEntityService<E, R>
{
    public EmailKeyedEntityService(R repository)
    {
        super(repository);
    }

    public E find(String emailAddress)
    {
        return repository.findDistinctByEmailAddress(emailAddress).orElseThrow(() -> new RuntimeException("Id does not exist"));
    }

    public boolean exist(String emailAddress)
    {
        return repository.existsByEmailAddress(emailAddress);
    }
}
