import com.cloudinary.Cloudinary;
import java.io.File;
import com.cloudinary.utils.ObjectUtils;
import java.io.*;


public class imageTest{
    public static void main(String[] args)
    {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dofenynug",
                "api_key", "731782633227963",
                "api_secret", "sBTjoeHlJNL79ai2KYoKGrC3w3c"));

        File toUpload = new File("daisy.png");
        try {
            cloudinary.uploader().upload("C:\\Users\\Han Cho\\Desktop\\Nano\\phishingrod\\src\\main\\java\\testing.jpg", ObjectUtils.emptyMap());
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}