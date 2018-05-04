package com.phishingrod.core.repository.base;

import com.phishingrod.core.domain.base.BasicEntity;
import com.phishingrod.core.domain.base.IEmailKeyEntity;
import com.phishingrod.core.repository.BasicEntityRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface EmailKeyEntityRepository<E extends BasicEntity & IEmailKeyEntity> extends BasicEntityRepository<E>
{
    Optional<E> findDistinctByEmailAddress(String emailAddress);

    boolean existsByEmailAddress(String emailAddress);
}
