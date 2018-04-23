package com.phishingrod.services.entity;

import com.phishingrod.domain.components.NameKeyedEntity;
import com.phishingrod.domain.components.PhishingRodEntity;
import com.phishingrod.repositories.base.NameKeyedRepository;

public abstract class NameKeyedEntityService<E extends PhishingRodEntity & NameKeyedEntity, R extends NameKeyedRepository<E>> extends GeneralEntityService<E, R>
{
    public NameKeyedEntityService(R repository)
    {
        super(repository);
    }

    public E find(String name)
    {
        return repository.findDistinctByName(name).orElseThrow(() -> new RuntimeException("Id does not exist"));
    }

    public boolean exist(String name)
    {
        return repository.existsByName(name);
    }
}
