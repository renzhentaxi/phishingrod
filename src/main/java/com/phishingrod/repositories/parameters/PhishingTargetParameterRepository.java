package com.phishingrod.repositories.parameters;

import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.domain.parameters.Parameter;
import com.phishingrod.domain.parameters.PhishingTargetParameter;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PhishingTargetParameterRepository extends PagingAndSortingRepository<PhishingTargetParameter, Long>
{
    Optional<PhishingTargetParameter> findDistinctByPhishingTargetAndParameter(PhishingTarget target, Parameter parameter);

}
