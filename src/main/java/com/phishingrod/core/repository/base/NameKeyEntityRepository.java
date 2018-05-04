package com.phishingrod.core.repository.base;

import com.phishingrod.core.domain.base.BasicEntity;
import com.phishingrod.core.domain.base.INameKeyEntity;
import com.phishingrod.core.repository.BasicEntityRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface NameKeyEntityRepository<E extends BasicEntity & INameKeyEntity> extends BasicEntityRepository<E>
{
    Optional<E> findDistinctByName(String name);

    boolean existsByName(String name);
}
