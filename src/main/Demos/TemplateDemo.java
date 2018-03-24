import Util.IOUtil;

public class TemplateDemo
{

    public static void main(String[] args)
    {
        String config = IOUtil.LoadEmail("TemplateDemo/config");
        String htmlSource = IOUtil.LoadEmail("TemplateDemo/index");
        ITemplateBuilder templateBuilder = new HtmlTemplateBuilder("{[", "]}");

        HtmlMailTemplate template = (HtmlMailTemplate) templateBuilder.build(htmlSource, config);

        IOUtil.SaveEmail("TemplateDemo/template", template.getSource());



    }


}
