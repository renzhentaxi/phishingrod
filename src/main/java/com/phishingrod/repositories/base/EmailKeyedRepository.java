package com.phishingrod.repositories.base;

import com.phishingrod.domain.components.EmailKeyedEntity;
import com.phishingrod.domain.components.PhishingRodEntity;
import com.phishingrod.domain.phishingTarget.PhishingTarget;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface EmailKeyedRepository<E extends PhishingRodEntity & EmailKeyedEntity> extends CrudRepository<E, Long>
{
    Optional<PhishingTarget> findDistinctByEmailAddress(String emailAddress);
    boolean existsByEmailAddress(String emailAddress);
}
