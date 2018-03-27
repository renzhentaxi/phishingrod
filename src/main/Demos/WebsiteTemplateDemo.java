import Util.IOUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;



public class WebsiteTemplateDemo {
    public static void main(String[] args) {

        Document doc;
        String webName;
        try {
            doc = Jsoup.connect("http://facebook.com").get();
            webName = doc.title();
            IOUtil.SaveWebsite(webName, doc.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
