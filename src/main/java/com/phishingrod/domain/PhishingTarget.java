//package com.phishingrod.domain;
//
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonView;
//import com.phishingrod.api.RestView;
//import com.phishingrod.domain.base.EmailedEntity;
//import com.phishingrod.domain.parameters.ParameterContainer;
//import com.phishingrod.domain.parameters.PhishingTargetParameter;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//public class PhishingTarget extends EmailedEntity implements ParameterContainer<PhishingTargetParameter>
//{
//
//
//    //parameter System
//    @OneToMany(mappedBy = "phishingTarget", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    private List<PhishingTargetParameter> parameterList = new ArrayList<>();
//
//    @Transient
//    @JsonView(RestView.Get.class)
//    @JsonProperty("parameters")
//    private Map<String, String> parameterMap;
//
//    public PhishingTarget(String emailAddress)
//    {
//        super(emailAddress);
//        parameterMap = new HashMap<>();
//    }
//
//    public PhishingTarget(String emailAddress, Map<String, String> parameterMap)
//    {
//        super(emailAddress);
//        this.parameterMap = parameterMap;
//    }
//}
