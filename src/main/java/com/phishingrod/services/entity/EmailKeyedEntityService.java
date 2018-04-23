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

    public E get(String emailAddress)
    {
        E entity = find(emailAddress);
        return postGet(entity);
    }

    public E createIfDoesNotExist(E entity)
    {
        if (exist(entity.getEmailAddress())) return get(entity.getEmailAddress());
        else return postGet(add(entity));
    }
}
