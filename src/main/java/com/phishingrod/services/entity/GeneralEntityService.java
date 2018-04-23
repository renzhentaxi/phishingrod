package com.phishingrod.services.entity;

import com.phishingrod.domain.components.PhishingRodEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class GeneralEntityService<E extends PhishingRodEntity, R extends CrudRepository<E, Long>> implements EntityService<E>
{
    protected R repository;

    public GeneralEntityService(R repository)
    {
        this.repository = repository;
    }

    protected abstract void merge(E source, E change);

    protected abstract void preAdd(E entity);

    protected abstract void preMod(E entity);

    protected abstract E postGet(E entity);

    public E find(long id)
    {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("does not exist"));
    }

    public boolean exist(long id)
    {
        return repository.existsById(id);
    }

    public void delete(long id)
    {
        if (repository.existsById(id)) repository.deleteById(id);
        else throw new RuntimeException("Does not exist");
    }

    public E add(E entity)
    {
        preAdd(entity);
        return repository.save(entity);
    }

    public E modify(E entity)
    {
        E original = find(entity.getId());
        merge(original, entity);
        preMod(original);
        return repository.save(original);
    }

    public E get(long id)
    {
        E entity = find(id);
        return postGet(entity);
    }

    public List<E> all()
    {

        List<E> list = new ArrayList<>();
        repository.findAll().forEach(e -> list.add(postGet(e)));
        return list;
    }
}
