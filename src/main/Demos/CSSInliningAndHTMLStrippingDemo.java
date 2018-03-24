import MailSystem.services.ICSSInliner;
import MailSystem.services.IHtmlStripper;
import MailSystem.services.PremailerService;
import Util.IOUtil;

public class CSSInliningAndHTMLStrippingDemo
{
    public static void main(String[] args) throws Exception
    {
        PremailerService premailerService = new PremailerService();
        ICSSInliner inliner = premailerService;
        IHtmlStripper stripper = premailerService;


        System.out.println("Loading test Email");
        String htmlToInline = IOUtil.LoadEmail("TemplateDemo/index");


        System.out.println("Inlining html");
        String inlinedHtml = inliner.inline(htmlToInline);
        System.out.println("Saving Inlined html");
        IOUtil.SaveEmail("TemplateDemo/inlined", inlinedHtml);

        System.out.println("Stripping html");
        String plainText = stripper.strip(htmlToInline);
        System.out.println("Saving Stripped html");
        IOUtil.SaveEmail("TemplateDemo/plainText", plainText);
        IOUtil.SaveEmail("TemplateDemo/config", plainText);
    }
}
