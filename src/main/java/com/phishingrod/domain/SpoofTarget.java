package com.phishingrod.domain;

import com.phishingrod.domain.base.EmailedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SpoofTarget extends EmailedEntity
{
    @ManyToMany(mappedBy = "spoofTargets")
    private List<EmailTemplate> templates = new ArrayList<>();

    public SpoofTarget(String emailAddress, Date dateAdded)
    {
        super(emailAddress, dateAdded);
    }

}
