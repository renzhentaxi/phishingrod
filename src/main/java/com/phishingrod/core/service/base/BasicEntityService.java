package com.phishingrod.core.service.base;

import com.phishingrod.core.domain.base.BasicEntity;
import com.phishingrod.core.exceptions.UnknownIdValidationException;
import com.phishingrod.core.repository.BasicEntityRepository;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.*;

import static com.phishingrod.core.service.base.EntityServiceAddonType.*;

@Transactional
public abstract class BasicEntityService<E extends BasicEntity, R extends BasicEntityRepository<E>>
{
    public String entityType;
    private Map<EntityServiceAddonType, List<EntityServiceAddon<E>>> addonRegistry;

    protected R repository;

    public BasicEntityService(R repository, String entityType)
    {
        this.repository = repository;
        this.entityType = entityType;
        this.addonRegistry = new HashMap<>();
        Arrays.stream(values()).forEach(entityServiceAddonType -> addonRegistry.put(entityServiceAddonType, new ArrayList<>()));
    }

    public void registerAddon(EntityServiceAddonType addonType, EntityServiceAddon<E> addon)
    {
        addonRegistry.get(addonType).add(addon);
    }

    protected E runAddons(E entity, EntityServiceAddonType type)
    {
        for (EntityServiceAddon<E> addon : addonRegistry.get(type))
        {
            entity = addon.act(entity);
        }
        return entity;
    }

    protected E runAddons(E entity, EntityServiceAddonType... types)
    {
        for (EntityServiceAddonType type : types)
            entity = runAddons(entity, type);
        return entity;
    }

    public E find(long id)
    {
        return repository.findById(id).orElseThrow(() -> new UnknownIdValidationException(entityType, "id", id));
    }

    public boolean exist(long id)
    {
        return repository.existsById(id);
    }

    public void delete(long id)
    {
        E entity = find(id);
        repository.delete(entity);
    }

    public E get(long id)
    {
        E entity = find(id);
        return runAddons(entity, postExtract);
    }

    public E getRandom()
    {
        long count = repository.count();
        if (count == 0) return null;
        int index = (int) (Math.random() * repository.count());
        E entity = repository.findAll(PageRequest.of(index, 1)).getContent().get(0);
        return runAddons(entity, postExtract);
    }

    public List<E> all()
    {

        List<E> list = new ArrayList<>();

        repository.findAll().forEach(e ->
        {
            list.add(runAddons(e, postExtract));
        });

        return list;
    }

    /**
     * adds the given entity to the database.
     * has 3 stages
     * addValidation: throws addValidation errors, checks that the entity is a valid entity
     * preAdd: attach/modify the entity such as setting the createdOn,lastModifiedOn dates
     * preInsert: transform the entity such that it is suitable for insertion.
     * after the 3 stages, the entity is saved into the database
     *
     * @param entity
     * @return
     */
    public E add(E entity)
    {
        return repository.save(runAddons(entity, addValidation, preAdd, preInsert));
    }

    public E modify(E entity)
    {
        return repository.save(runAddons(entity, modValidation, preMod, preInsert));
    }
}
