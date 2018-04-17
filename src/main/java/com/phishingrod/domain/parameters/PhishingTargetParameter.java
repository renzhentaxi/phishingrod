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
@Table(
        uniqueConstraints = @UniqueConstraint(name = "uk_targetParameter", columnNames = {"phishingTarget", "parameter"})
)
public class PhishingTargetParameter
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="phishingTarget")
    private PhishingTarget phishingTarget;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="parameter")
    private Parameter parameter;

    private String value;

    public PhishingTargetParameter(PhishingTarget phishingTarget, Parameter parameter, String value)
    {

        this.phishingTarget = phishingTarget;
        this.parameter = parameter;
        this.value = value;
    }
}
