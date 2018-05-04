package com.phishingrod.core.service.base;

import com.phishingrod.core.domain.base.BasicEntity;
import com.phishingrod.core.domain.base.IEmailKeyEntity;
import com.phishingrod.core.exceptions.DuplicateIdValidationException;
import com.phishingrod.core.exceptions.UnknownIdValidationException;
import com.phishingrod.core.repository.base.EmailKeyEntityRepository;

import static com.phishingrod.core.service.base.EntityServiceAddonType.addValidation;
import static com.phishingrod.core.service.base.EntityServiceAddonType.postExtract;

public abstract class EmailKeyedEntityService<E extends BasicEntity & IEmailKeyEntity, R extends EmailKeyEntityRepository<E>> extends BasicEntityService<E, R>
{
    public EmailKeyedEntityService(R repository, String entityType)
    {
        super(repository, entityType);
        registerAddon(addValidation, this::checkEmailIsNotDuplicate);
    }

    private E checkEmailIsNotDuplicate(E entity)
    {
        if (exist(entity.getEmailAddress()))
        {
            throw new DuplicateIdValidationException(entity, "emailAddress", entity.getEmailAddress());
        }
        return entity;
    }

    public E find(String emailAddress)
    {
        return repository.findDistinctByEmailAddress(emailAddress).orElseThrow(() -> new UnknownIdValidationException(entityType, "emailAddress", emailAddress));
    }

    public boolean exist(String emailAddress)
    {
        return repository.existsByEmailAddress(emailAddress);
    }

    public void delete(String emailAddress)
    {
        E entity = find(emailAddress);
        repository.delete(entity);
    }

    public E get(String emailAddress)
    {
        E entity = find(emailAddress);
        return runAddons(entity, postExtract);
    }
}
