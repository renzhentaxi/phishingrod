package com.phishingrod.core.service.base;

import com.phishingrod.core.domain.base.BasicEntity;
import com.phishingrod.core.domain.base.INameKeyEntity;
import com.phishingrod.core.exceptions.DuplicateIdValidationException;
import com.phishingrod.core.exceptions.UnknownIdValidationException;
import com.phishingrod.core.repository.base.NameKeyEntityRepository;

import static com.phishingrod.core.service.base.EntityServiceAddonType.addValidation;
import static com.phishingrod.core.service.base.EntityServiceAddonType.postExtract;

public abstract class NameKeyedEntityService<E extends BasicEntity & INameKeyEntity, R extends NameKeyEntityRepository<E>> extends BasicEntityService<E, R>
{
    public NameKeyedEntityService(R repository, String entityType)
    {
        super(repository, entityType);
        registerAddon(addValidation, this::checkNameIsNotDuplicate);
    }

    public E find(String name)
    {
        return repository.findDistinctByName(name)
                .orElseThrow(() -> new UnknownIdValidationException(entityType, "name", name));
    }

    private E checkNameIsNotDuplicate(E entity)
    {
        if (exist(entity.getName()))
        {
            throw new DuplicateIdValidationException(entity, "name", entity.getName());
        }
        return entity;
    }

    public boolean exist(String name)
    {
        return repository.existsByName(name);
    }

    public void delete(String name)
    {
        E entity = find(name);
        repository.delete(entity);
    }

    public E get(String name)
    {
        E entity = find(name);
        return runAddons(entity, postExtract);
    }
}
