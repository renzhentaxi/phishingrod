//package com.phishingrod.test.parameter;
//
//
//import com.phishingrod.domain.components.PhishingRodEntity;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.MappedSuperclass;
//
//@MappedSuperclass
//@Getter
//@Setter
//@NoArgsConstructor
//public class EntityParameterLink<E extends PhishingRodEntity> extends EntityParameter<E>
//{
//    @ManyToOne
//    @JoinColumn(name = "target")
//    private E target;
//
//    private String value;
//}
