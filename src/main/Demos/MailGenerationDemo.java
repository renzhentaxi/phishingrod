import Util.IOUtil;

import java.util.HashMap;
import java.util.Map;

public class MailGenerationDemo
{
    public static void main(String[] args)
    {
        String templateHtml = IOUtil.LoadEmail("TemplateDemo/template");
        HtmlMailTemplate template = new HtmlMailTemplate(templateHtml);

        Map<String, String> parameters = new HashMap<>();

        parameters.put("addres", "Taxi's shop");
        parameters.put("name", "Kaylee");
        IOUtil.SaveEmail("TemplateDemo/generated", template.generateMail(parameters));

    }
}
