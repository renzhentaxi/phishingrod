//package com.phishingrod.services;
//
//import Util.HttpPoster;
//import Util.IOUtil;
//import Util.PostParam;
//import com.phishingrod.services.api.ICSSInliner;
//import com.phishingrod.services.api.IHtmlStripper;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.impl.client.HttpClients;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//
//import java.io.IOException;
//
//
//public class PremailerService implements ICSSInliner, IHtmlStripper
//{
//    private enum ExtractType
//    {
//        plaintext("#txt_output"),
//        inlinedHtml("#html_output");
//
//        public final String type;
//
//        ExtractType(String type)
//        {
//            this.type = type;
//        }
//    }
//
//    private HttpPoster poster;
//
//    public PremailerService()
//    {
//        poster = new HttpPoster(HttpClients.createDefault(), "http://premailer.dialect.ca");
//    }
//
//    private String parseResponse(HttpResponse response)
//    {
//        String responseHtml = "";
//        try
//        {
//            HttpEntity responseEntity = response.getEntity();
//            responseHtml = IOUtil.LoadStream(responseEntity.getContent());
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        return responseHtml;
//    }
//
//    private PostParam makeParam(String template)
//    {
//        PostParam param = new PostParam();
//        param.add("content_source", "template");
//        param.add("template", template);
//        return param;
//    }
//
//    private String run(String template)
//    {
//        PostParam param = makeParam(template);
//        HttpResponse response = poster.post(param);
//        String responseParsed = parseResponse(response);
//        if (responseParsed.isEmpty())
//        {
//            throw new RuntimeException("Premail service failed!!");
//        }
//        return responseParsed;
//    }
//
//    private String extract(String responseParsed, ExtractType extractType)
//    {
//        Document doc = Jsoup.parse(responseParsed);
//        Element inlinedElement = doc.selectFirst(extractType.type);
//        return inlinedElement.text();
//    }
//
//
//    @Override
//    public String inline(String template)
//    {
//        String response = run(template);
//        return extract(response, ExtractType.inlinedHtml);
//    }
//
//    @Override
//    public String strip(String template)
//    {
//        String response = run(template);
//        return extract(response, ExtractType.plaintext);
//    }
//}
