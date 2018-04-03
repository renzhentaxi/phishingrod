package com.phishingrod.domain;


import com.phishingrod.domain.base.PhishingRodEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sender extends PhishingRodEntity
{
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private SenderServer server;

    @Column(nullable = false)
    private String loginName;

    @Column(nullable = false)
    private String password;

    public Sender(SenderServer server, String loginName, String password, Date dateAdded)
    {
        super(dateAdded);
        this.server = server;
        this.loginName = loginName;
        this.password = password;
    }
}
