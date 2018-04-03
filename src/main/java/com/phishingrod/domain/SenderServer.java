package com.phishingrod.domain;

import com.phishingrod.domain.base.NamedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SenderServer extends NamedEntity
{
    @OneToMany(mappedBy = "server", orphanRemoval = true)
    private List<Sender> senders = new ArrayList<>();

    @Column(nullable = false)
    private String host;
    @Column(nullable = false)
    private boolean tls;
    @Column(nullable = false)
    private int port;

    public SenderServer(String name, String host, boolean useTls, int port, Date timeAdded)
    {
        super(timeAdded, name);
        this.host = host;
        this.tls = useTls;
        this.port = port;
    }
}
