package com.phishingrod.core.service.htmlParser;

import com.phishingrod.core.domain.Attempt;
import com.phishingrod.core.domain.EmailTemplate;
import com.phishingrod.core.domain.PhishingTarget;
import com.phishingrod.core.domain.SpoofTarget;
import com.phishingrod.core.domain.parameters.ParameterSourceType;
import com.phishingrod.core.exceptions.MissingParameterValidationException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.Map;

public class HtmlParser
{
    private TemplateConfig configuration;

    public HtmlParser(TemplateConfig configuration)
    {
        this.configuration = configuration;
    }

//    private String parse(String error_message, Map<String, String> spoofTargetParameters, Map<String, String> phishingTargetParameters)
//    {
//        Document document = Jsoup.parse(error_message);
//
//        Elements parameterNodes = document.select("." + configuration.class_parameterNode);
//        parameterNodes.forEach(element ->
//                {
//                    String name = element.attr(configuration.attribute_parameterName);
//                    ParameterSourceType error_type = ParameterSourceType.valueOf(element.attr(configuration.attribute_parameterType));
//                    String value = (error_type == ParameterSourceType.phishingTarget) ? phishingTargetParameters.get(name) : spoofTargetParameters.get(name);
//                    if (value == null) throw new MissingParameterValidationException(error_type.name(), name);
//                    TextNode node = new TextNode(value);
//                    element.replaceWith(node);
//                }
//        );
//
//        return document.toString();
//    }

    public String parse(EmailTemplate template, SpoofTarget spoofTarget, PhishingTarget phishingTarget)
    {

        String message = template.getHtml();
        Map<String, String> spoofTargetParameters = spoofTarget.getParameterMap();
        Map<String, String> phishingTargetParameters = phishingTarget.getParameterMap();
        Document document = Jsoup.parse(message);

        Elements parameterNodes = document.select("." + configuration.class_parameterNode);
        parameterNodes.forEach(element ->
                {
                    String name = element.attr(configuration.attribute_parameterName);
                    ParameterSourceType type = ParameterSourceType.valueOf(element.attr(configuration.attribute_parameterType));
                    String value = "Missing";
                    if (type == ParameterSourceType.phishingTarget && (value = phishingTargetParameters.get(name)) == null)
                    {
                        throw new MissingParameterValidationException(type.name(), phishingTarget.getEmailAddress(), name);
                    } else if (type == ParameterSourceType.spoofTarget && (value = spoofTargetParameters.get(name)) == null)
                    {
                        throw new MissingParameterValidationException(type.name(), spoofTarget.getEmailAddress(), name);
                    } else
                    {
                        TextNode node = new TextNode(value);
                        element.replaceWith(node);
                    }
                }
        );

        return document.toString();
    }

    public String parse(Attempt attempt)
    {
        return parse(attempt.getTemplate(), attempt.getSpoofTarget(), attempt.getPhishingTarget());
    }
}