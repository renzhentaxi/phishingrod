package MailSystem.services;

import Util.HttpPoster;
import Util.IOUtil;
import Util.PostParam;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;


public class PremailerService implements ICSSInliner, IHtmlStripper
{
    private enum ExtractType
    {
        plaintext("#txt_output"),
        inlinedHtml("#html_output");

        public final String type;

        ExtractType(String type)
        {
            this.type = type;
        }
    }

    private HttpPoster poster;

    public PremailerService()
    {
        poster = new HttpPoster(HttpClients.createDefault(), "http://premailer.dialect.ca");
    }

    private String parseResponse(HttpResponse response)
    {
        String responseHtml = "";
        try
        {
            HttpEntity responseEntity = response.getEntity();
            responseHtml = IOUtil.LoadStream(responseEntity.getContent());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return responseHtml;
    }

    private PostParam makeParam(String html)
    {
        PostParam param = new PostParam();
        param.add("content_source", "html");
        param.add("html", html);
        return param;
    }

    private String run(String html)
    {
        PostParam param = makeParam(html);
        HttpResponse response = poster.post(param);
        String responseParsed = parseResponse(response);
        if (responseParsed.isEmpty())
        {
            throw new RuntimeException("Premail service failed!!");
        }
        return responseParsed;
    }

    private String extract(String responseParsed, ExtractType extractType)
    {
        Document doc = Jsoup.parse(responseParsed);
        Element inlinedElement = doc.selectFirst(extractType.type);
        return inlinedElement.text();
    }


    @Override
    public String inline(String html)
    {
        String response = run(html);
        return extract(response, ExtractType.inlinedHtml);
    }

    @Override
    public String strip(String html)
    {
        String response = run(html);
        return extract(response, ExtractType.plaintext);
    }
}
