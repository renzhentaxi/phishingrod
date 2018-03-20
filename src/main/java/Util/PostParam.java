package Util;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PostParam
{
    List<NameValuePair> model;

    public PostParam()
    {
        model = new ArrayList<>();
    }

    public void add(String name, String value)
    {
        model.add(new BasicNameValuePair(name, value));
    }


    public UrlEncodedFormEntity toEntity()
    {
        try
        {
            return new UrlEncodedFormEntity(model, "UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
