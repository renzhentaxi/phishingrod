//package com.phishingrod.domain.parameters;
//
//import com.phishingrod.domain.PhishingTarget;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@Table(
//        uniqueConstraints = @UniqueConstraint(name = "uk_targetParameter", columnNames = {"phishingTarget", "parameter"})
//)
//public class PhishingTargetParameter extends EntityParameter
//{
//
//    @ManyToOne
//    @JoinColumn(name = "phishingTarget")
//    private PhishingTarget phishingTarget;
//
//
//    public PhishingTargetParameter(PhishingTarget phishingTarget, Parameter parameter)
//    {
//        super(parameter);
//        this.phishingTarget = phishingTarget;
//    }
//
//    public PhishingTargetParameter(PhishingTarget phishingTarget, Parameter parameter, String value)
//    {
//        super(parameter, value);
//        this.phishingTarget = phishingTarget;
//    }
//}
