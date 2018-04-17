package com.phishingrod.domain.parameters;

import com.phishingrod.domain.PhishingTarget;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PhishingTargetParameter
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private PhishingTarget phishingTarget;

    @ManyToOne
    private Parameter parameter;

    private String value;

    public PhishingTargetParameter(PhishingTarget phishingTarget, Parameter parameter, String value)
    {

        this.phishingTarget = phishingTarget;
        this.parameter = parameter;
        this.value = value;
    }
}
