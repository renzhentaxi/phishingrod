package com.phishingrod.core.repository;

import com.phishingrod.core.domain.base.BasicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BasicEntityRepository<E extends BasicEntity> extends CrudRepository<E, Long>
{
    Page<E> findAll(Pageable pageable);
}
