package com.phishingrod.core.domain;

import com.phishingrod.core.domain.combinations.StatTrackingNameKeyEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"port", "host"})
)
public class SenderServer extends StatTrackingNameKeyEntity
{

    public SenderServer(String name, int port, String host)
    {
        super(name);
        this.port = port;
        this.host = host;
    }

    @Column(nullable = false)
    private int port;

    @Column(nullable = false)
    private String host;

}
