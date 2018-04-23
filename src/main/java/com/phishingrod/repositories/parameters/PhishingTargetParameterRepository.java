package com.phishingrod.repositories.parameters;

import com.phishingrod.domain.components.params.Parameter;
import com.phishingrod.domain.phishingTarget.PhishingTarget;
import com.phishingrod.domain.phishingTarget.PhishingTargetParameter;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PhishingTargetParameterRepository extends PagingAndSortingRepository<PhishingTargetParameter, Long>
{
    Optional<PhishingTargetParameter> findDistinctByEntityAndParameter(PhishingTarget entity, Parameter parameter);

}
