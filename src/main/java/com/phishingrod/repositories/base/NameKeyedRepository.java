package com.phishingrod.repositories.base;

import com.phishingrod.domain.components.NameKeyedEntity;
import com.phishingrod.domain.components.PhishingRodEntity;
import com.phishingrod.domain.phishingTarget.PhishingTarget;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface NameKeyedRepository<E extends PhishingRodEntity & NameKeyedEntity> extends CrudRepository<E, Long>
{
    Optional<PhishingTarget> findDistinctByName(String name);

    boolean existsByName(String name);
}
