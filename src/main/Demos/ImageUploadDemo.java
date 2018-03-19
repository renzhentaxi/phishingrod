import MailSystem.imageUploaders.CloudinaryImageUploader;
import MailSystem.imageUploaders.IImageUploader;

import java.util.Map;


public class ImageUploadDemo
{
    public static void main(String[] args)
    {
        Map config = CloudiaryConfigProvider.provideConfig();
        IImageUploader imageUploader = new CloudinaryImageUploader(config);
        String imageLocation = "Data/DemoData/ImageUploadDemo/testing.jpg";
        String uploadedLocation = imageUploader.upload(imageLocation);

        System.out.println("Image is uploaded to : " + uploadedLocation);
    }
}