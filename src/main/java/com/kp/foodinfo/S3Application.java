package com.kp.foodinfo;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class S3Application {
    public static void main(String[] args) throws FileNotFoundException {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIASDJ2G6ITZ6B6VQEU", "RyAHVdxwZqwQDUe80svc6WDqjn6fLXeSDbAwLN+T");
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
        FileInputStream fis = new FileInputStream("test.jpg");

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("image/jpeg");

        String fileName = "profileprofile.jpg";

        PutObjectRequest putObjectRequest = new PutObjectRequest("foodinfotest-bucket2", fileName, fis, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);

        try {
            s3Client.putObject(putObjectRequest);
            // upload success

            String url = String.format("https://%s.s3.ap-northeast-2.amazonaws.com/%s", "foodinfotest-bucket2", fileName);

            System.out.println("upload url : " + url);

            // s3Client.getUrl()
            // update or insert to db ... url ...

        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }
}
