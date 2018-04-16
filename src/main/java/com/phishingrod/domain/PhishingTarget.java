package com.phishingrod.domain;


import com.phishingrod.domain.base.EmailedEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@NoArgsConstructor
public class PhishingTarget extends EmailedEntity
{
    public PhishingTarget(String emailAddress)
    {
        super(emailAddress);
    }
}
