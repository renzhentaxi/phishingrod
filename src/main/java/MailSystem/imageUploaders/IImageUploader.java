package MailSystem.imageUploaders;

public interface IImageUploader
{
    /**
     * uploads the image and returns the url for it
     * @param imagePath the path to the image
     * @return the url of the uploaded image
     */
    String upload(String imagePath);


}
