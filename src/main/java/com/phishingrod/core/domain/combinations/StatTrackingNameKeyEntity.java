package com.phishingrod.core.domain.combinations;

import com.phishingrod.core.domain.base.INameKeyEntity;
import com.phishingrod.core.domain.base.StatTrackingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class StatTrackingNameKeyEntity extends StatTrackingEntity implements INameKeyEntity
{
    @Column(unique = true, nullable = false)
    private String name;

    public StatTrackingNameKeyEntity(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatTrackingNameKeyEntity that = (StatTrackingNameKeyEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }
}
