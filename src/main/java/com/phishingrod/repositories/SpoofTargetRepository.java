package com.phishingrod.repositories;

import com.phishingrod.domain.SpoofTarget;
import org.springframework.data.repository.CrudRepository;

public interface SpoofTargetRepository extends CrudRepository<SpoofTarget, Long>
{
}
