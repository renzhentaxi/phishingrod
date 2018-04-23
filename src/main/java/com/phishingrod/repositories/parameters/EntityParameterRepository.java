package com.phishingrod.repositories.parameters;

import com.phishingrod.domain.components.PhishingRodEntity;
import com.phishingrod.domain.parameters.EntityParameter;
import com.phishingrod.domain.parameters.Parameter;
import com.phishingrod.domain.parameters.ParameterContainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface EntityParameterRepository<P extends EntityParameter<E>, E extends PhishingRodEntity & ParameterContainer<E, P>> extends CrudRepository<P, Long>
{
    Optional<P> findDistinctByEntityAndParameter(E entity, Parameter parameter);
}
