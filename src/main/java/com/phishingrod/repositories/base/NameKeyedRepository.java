package com.phishingrod.repositories.base;

import com.phishingrod.domain.components.NameKeyedEntity;
import com.phishingrod.domain.components.PhishingRodEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface NameKeyedRepository<E extends PhishingRodEntity & NameKeyedEntity> extends CrudRepository<E, Long>
{
    Optional<E> findDistinctByName(String name);

    boolean existsByName(String name);
}
