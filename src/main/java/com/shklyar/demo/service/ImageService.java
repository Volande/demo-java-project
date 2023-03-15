package com.shklyar.demo.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class ImageService {

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
}
