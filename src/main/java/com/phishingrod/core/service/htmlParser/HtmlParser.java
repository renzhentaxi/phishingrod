package com.phishingrod.core.service.htmlParser;

import com.phishingrod.core.domain.Attempt;
import com.phishingrod.core.domain.EmailTemplate;
import com.phishingrod.core.domain.PhishingTarget;
import com.phishingrod.core.domain.SpoofTarget;
import com.phishingrod.core.domain.parameters.ParameterSourceType;
import com.phishingrod.core.exceptions.MissingParameterValidationException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

    /**
     * swaps every parameter in the template with actual values provided by spoofTarget and phishingTarget
     *
     * @param template       the template containing the html to parse
     * @param spoofTarget    the spoofTarget that contains the parameters required
     * @param phishingTarget the spoofTarget that contains the parameters required
     * @return a html document with every parameter parsed
     */
    public Document parseParams(EmailTemplate template, SpoofTarget spoofTarget, PhishingTarget phishingTarget)
    {

        String message = template.getHtml();
        Map<String, String> spoofTargetParameters = spoofTarget.getParameterMap();
        Map<String, String> phishingTargetParameters = phishingTarget.getParameterMap();
        Document document = Jsoup.parse(message);

        Elements parameterNodes = document.select(configuration.class_parameterNode);
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

        return document;
    }

    /**
     * replaces link on this document with track url
     *
     * @param document  the document to work with
     * @param attemptId the id to track
     * @return a document with links replaced
     */
    public Document replaceLinkWithTrackLink(Document document, long attemptId)
    {
        String url = "https://protected-reef-24647.herokuapp.com//api/attempt/" + attemptId + "/track";
        Elements linkNodes = document.select("a");
        linkNodes.forEach(element -> element.attr("href", url));
        return document;
    }

    public Document installOpenDetector(Document document, long attemptId)
    {
        String url = "https://protected-reef-24647.herokuapp.com/api/attempt/" + attemptId + "/open";
        Element image = new Element("img");
        image.attr("src", url);
        image.attr("alt", "");
        document.body().appendChild(image);
        return document;
    }

    public String parse(Attempt attempt)
    {
        Document document = parseParams(attempt.getTemplate(), attempt.getSpoofTarget(), attempt.getPhishingTarget());
        document = replaceLinkWithTrackLink(document, attempt.getId());
        document = installOpenDetector(document, attempt.getId());
        return document.toString();
    }
}