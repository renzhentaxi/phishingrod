package MailSystem.imageUploaders;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.Map;

public class CloudinaryImageUploader implements IImageUploader
{
    Cloudinary cloudinary;

    public CloudinaryImageUploader(Map authenticationConfig)
    {
        cloudinary = new Cloudinary(authenticationConfig);
    }

    @Override
    public String upload(String imagePath)
    {
        try
        {
            Map map = cloudinary.uploader().upload(imagePath, ObjectUtils.emptyMap());
            return map.get("url").toString();
        } catch (IOException exception)
        {
            exception.printStackTrace();
            return null;
        }
    }
}
