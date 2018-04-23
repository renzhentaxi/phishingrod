package com.phishingrod.services.entity;

import com.phishingrod.domain.components.PhishingRodEntity;

import java.util.List;

public interface EntityService<E extends PhishingRodEntity>
{

    E get(long id);

    List<E> all();

    E add(E entity);

    E modify(E entity);

    boolean exist(long id);

    void delete(long id);
}
