package com.shklyar.demo.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.shklyar.demo.dao.ImageRepository;
import com.shklyar.demo.dao.ProductRepository;
import com.shklyar.demo.entities.Collection;
import com.shklyar.demo.entities.Images;
import com.shklyar.demo.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service("ImageService")
public class ImageService {
    @Autowired
    public ImageService(ImageRepository imageRepository, ProductRepository productRepository) {
        this.imageRepository = imageRepository;
        this.productRepository=productRepository;
    }

    @Value("${image.service.repository}")
    String imagesDirectory;

    //private AmazonS3 s3client;
    private AmazonS3 s3client;
    @Value("${amazon.bucketName}")
    private String bucketName;
    @Value("${amazon.accessKey}")
    private String accessKey;
    @Value("${amazon.secretKey}")
    private String secretKey;
    @Value("${amazon.region}")
    private String region;
    @Value("${imageCloudDirectory}")
    private String directory;

    ImageRepository imageRepository;
    ProductRepository productRepository;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.fromName(region))
                .build();

    }

    public void saveImage(String fileName, MultipartFile multipartFile, String fileExtension) {


        try {
            if (multipartFile == null) {
                throw new IllegalArgumentException();
            }

            ObjectMetadata dasd = new ObjectMetadata();

            dasd.addUserMetadata("Content-Type", "image/jpeg");


            BufferedImage image = ImageIO.read(multipartFile.getInputStream());

            File tempFile = new File(imagesDirectory + File.separator + "tempFile" + fileExtension);

            ImageIO.write(image, "png", tempFile);

            s3client.putObject(bucketName, directory + fileName, tempFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Images addImageToDatabase(String urlImage, Product product) {

        Images images = new Images();
        images.setTitle(urlImage);
        images.setProducts(product);
        imageRepository.save(images);

        return images;
    }



    public Images initImages(String urlImage) {

        Images newImage = imageRepository.getByTitle(urlImage);

        if (newImage == null) {
            newImage = new Images();
            newImage.setTitle(urlImage);
            imageRepository.save(newImage);
        }

        return newImage;
    }

    public boolean deleteImage(String imageURL) {
        if (imageURL == null) {
            return true;
        }

        imageURL = imageURL.substring(imageURL.lastIndexOf("/") + 1);

        s3client.deleteObject(bucketName, directory + imageURL);

        return true;
    }

    public void deleteUnusedImages(){
       List<Images> imagesList =  imageRepository.findImagesByProducts(null);
       for(Images image:imagesList){
           if(deleteImage(image.getTitle())) {
               imageRepository.delete(image);
           }
       }
    }
}
