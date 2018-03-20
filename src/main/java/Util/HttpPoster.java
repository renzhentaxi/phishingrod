package Util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;

public class HttpPoster
{
    private HttpPost httpPost;
    private HttpClient client;

    public HttpPoster(HttpClient client, String url)
    {
        this.client = client;
        httpPost = new HttpPost(url);
    }

    public HttpResponse post(PostParam param)
    {
        httpPost.setEntity(param.toEntity());
        HttpResponse response = null;
        try
        {
            response = client.execute(httpPost);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return response;
    }
}
