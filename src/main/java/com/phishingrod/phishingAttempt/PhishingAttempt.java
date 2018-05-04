//package com.phishingrod.phishingAttempt;
//
//import com.phishingrod.domain.components.PhishingRodEntity;
//import com.phishingrod.domain.phishingTarget.PhishingTarget;
//import com.phishingrod.emailTemplate.EmailTemplate;
//import com.phishingrod.spoofTarget.SpoofTarget;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.Entity;
//import java.util.Date;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//public class PhishingAttempt extends PhishingRodEntity
//{
//    //    @ManyToOne
//    private EmailTemplate template;
//    //    @ManyToOne
//    private PhishingTarget phishingTarget;
//    //    @ManyToOne
//    private SpoofTarget spoofTarget;
//
//    private Date date;
//
//    public PhishingAttempt(EmailTemplate template, PhishingTarget phishingTarget, SpoofTarget spoofTarget)
//    {
//        this.template = template;
//        this.phishingTarget = phishingTarget;
//        this.spoofTarget = spoofTarget;
//    }
//}
