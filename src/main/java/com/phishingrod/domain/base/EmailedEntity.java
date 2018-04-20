package com.phishingrod.domain.base;

import com.fasterxml.jackson.annotation.JsonView;
import com.phishingrod.api.RestView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


/**
 * This is an extension of PhishingRodEntity
 * Adds a required emailAddress column to the domain entity
 */
@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor
public class EmailedEntity extends PhishingRodEntity
{

    /**
     * the email address of the entity
     * It must be unique within the table and not null.
     */
    @Column(name = "email_address", unique = true, nullable = false)
    @JsonView(RestView.Get.class)
    private String emailAddress;


    public EmailedEntity(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }
}
