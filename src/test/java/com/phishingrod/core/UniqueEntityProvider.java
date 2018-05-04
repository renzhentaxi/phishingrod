package com.phishingrod.core;

import com.phishingrod.core.domain.EmailTemplate;
import com.phishingrod.core.domain.PhishingTarget;
import com.phishingrod.core.domain.SpoofTarget;
import com.phishingrod.core.domain.parameters.ParameterSourceType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UniqueEntityProvider
{
    private static AtomicInteger counter = new AtomicInteger();

    private final static String T_EMAIL_TEMPLATE_NAME = "emailTemplateName";
    private final static String T_EMAIL_TEMPLATE_HTML = "<b> Email Template Html</b>";
    private final static String T_SPOOF_TARGET_EMAIL = "spoofTarget@email.com";
    private final static String T_PHISHING_TARGET_EMAIL = "phishingTarget@email.com";

    public static String makeUnique(String base)
    {
        return base + counter.incrementAndGet();
    }

    public static EmailTemplate makeUniqueTemplate(String... parameters)
    {
        return makeUniqueTemplateWith(T_EMAIL_TEMPLATE_HTML, parameters);
    }

    public static EmailTemplate makeUniqueTemplateWith(String html, String... parameters)
    {
        EmailTemplate e = new EmailTemplate(makeUnique(T_EMAIL_TEMPLATE_NAME), html);
        for (String s : parameters)
        {
            e.addParameter(s.startsWith("s.") ? ParameterSourceType.spoofTarget : ParameterSourceType.phishingTarget, s.substring(2));
        }
        return e;
    }

    public static SpoofTarget makeUniqueSpoofTarget(String... parameters)
    {
        return new SpoofTarget(makeUnique(T_SPOOF_TARGET_EMAIL), convertToMap(parameters));
    }

    public static PhishingTarget makeUniquePhishingTarget(String... parameters)
    {
        return new PhishingTarget(makeUnique(T_PHISHING_TARGET_EMAIL), convertToMap(parameters));
    }

    private static Map<String, String> convertToMap(String... paramData)
    {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < paramData.length; i += 2)
        {
            map.put(paramData[i], paramData[i + 1]);
        }
        return map;
    }
}
