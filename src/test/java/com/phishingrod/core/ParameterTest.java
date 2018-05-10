//package com.phishingrod.core;
//
//import com.phishingrod.domain.parameters.Parameter;
//import com.phishingrod.domain.parameters.ParameterSourceType;
//import com.phishingrod.domain.phishingTarget.PhishingTarget;
//import com.phishingrod.emailTemplate.EmailTemplate;
//import com.phishingrod.services.EmailFactory;
//import com.phishingrod.services.PhishingTargetService;
//import com.phishingrod.spoofTarget.SpoofTarget;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ParameterTest
//{
//
//    private EmailFactory factory = new EmailFactory();
//
//    @Test
//    public void test()
//    {
//
//        Map<String, String> spoofTargetMap = new HashMap<>();
//        Map<String, String> phishingTargetMap = new HashMap<>();
//        List<Parameter> parameterList = new ArrayList<>();
//
//        spoofTargetMap.put("name", "taxi");
//        phishingTargetMap.put("name", "baerde");
//
//        parameterList.schedule(new Parameter(ParameterSourceType.phishingTarget, "name"));
//        parameterList.schedule(new Parameter(ParameterSourceType.spoofTarget, "name"));
//
//        String testHtml = "To: <div class=\"pr-param-node\" pr-param-name=\"phishingTarget.name\"> hi </div> " +
//                "From: <div class=\"pr-param-node\" pr-param-name=\"spoofTarget.name\"> hi2 </div>";
//
//
//        Map<String, String> computedMap = factory.computeParameterMap(parameterList, spoofTargetMap, phishingTargetMap);
//        System.out.println(computedMap);
//
//        String parsedHtml = factory.parseHtml(testHtml, computedMap);
//        System.out.println(parsedHtml);
//    }
//}
