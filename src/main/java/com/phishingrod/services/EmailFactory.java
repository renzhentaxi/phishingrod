package com.phishingrod.services;

import com.phishingrod.domain.parameters.Parameter;
import com.phishingrod.domain.phishingTarget.PhishingTarget;
import com.phishingrod.emailTemplate.EmailTemplate;
import com.phishingrod.emailTemplate.EmailTemplateService;
import com.phishingrod.spoofTarget.SpoofTarget;
import com.phishingrod.spoofTarget.SpoofTargetService;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@NoArgsConstructor
public class EmailFactory
{
    private EmailTemplateService emailTemplateService;
    private SpoofTargetService spoofTargetService;
    private PhishingTargetService phishingTargetService;

    @Autowired
    public EmailFactory(EmailTemplateService emailTemplateService, SpoofTargetService spoofTargetService, PhishingTargetService phishingTargetService)
    {
        this.emailTemplateService = emailTemplateService;
        this.spoofTargetService = spoofTargetService;
        this.phishingTargetService = phishingTargetService;
    }

    private static final String PR_NODE = "pr-param-node";
    private static final String PR_PARAM_NAME = "pr-param-name";

    //todo: assume everything is perfect
    public String from(EmailTemplate template, SpoofTarget from, PhishingTarget to)
    {
        Map<String, String> parameterMap = computeParameterMap(template.getParameters(), from.getParameterMap(), to.getParameterMap());
        return parseHtml(template.getSourceHtml(), parameterMap);
    }

    public String from(long templateId, long spoofTargetId, long phishingTargetId)
    {
        return from(emailTemplateService.get(templateId), spoofTargetService.get(spoofTargetId), phishingTargetService.get(phishingTargetId));
    }

    public Map<String, String> computeParameterMap(List<Parameter> parameterList, Map<String, String> spoofTargetParameterMap, Map<String, String> phishingTargetParameterMap)
    {
        Map<String, String> parameterMap = new HashMap<>();

        parameterList.forEach(
                parameter ->
                {
                    String fullName = "";
                    String value = "";
                    String paramName = parameter.getName();
                    switch (parameter.getSourceType())
                    {
                        case phishingTarget:
                            value = phishingTargetParameterMap.get(paramName);
                            fullName = "phishingTarget" + "." + paramName;
                            break;
                        case spoofTarget:
                            value = spoofTargetParameterMap.get(paramName);
                            fullName = "spoofTarget" + "." + paramName;
                            break;
                    }
                    parameterMap.put(fullName, value);
                }
        );

        return parameterMap;
    }

    public String parseHtml(String sourceHtml, Map<String, String> computedMap)
    {
        Document document = Jsoup.parse(sourceHtml);

        Elements elements = document.select("." + PR_NODE);
        for (Element e : elements)
        {
            String fullName = e.attr(PR_PARAM_NAME);
            String value = computedMap.get(fullName);
            TextNode node = new TextNode(value);
            e.replaceWith(node);
        }
        return document.toString();
    }
}
