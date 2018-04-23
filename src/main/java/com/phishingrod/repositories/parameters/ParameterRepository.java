package com.phishingrod.repositories.parameters;

import com.phishingrod.domain.components.params.Parameter;
import com.phishingrod.domain.parameters.ParameterSourceType;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ParameterRepository extends PagingAndSortingRepository<Parameter, Long>
{
    Optional<Parameter> findDistinctBySourceTypeAndName(ParameterSourceType sourceType, String name);

}

