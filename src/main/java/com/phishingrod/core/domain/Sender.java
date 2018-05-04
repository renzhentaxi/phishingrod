package com.phishingrod.core.domain;

import com.phishingrod.core.domain.combinations.StatTrackingNameKeyEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Sender extends StatTrackingNameKeyEntity
{
    @Column(nullable = false)
    private String password;

    @ManyToOne
    private SenderServer server;

    public Sender(String name, String password, SenderServer server)
    {
        super(name);
        this.password = password;
        this.server = server;
    }
}
