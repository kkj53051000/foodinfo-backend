package com.kp.foodinfo.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class FileService {

    String uuid = UUID.randomUUID().toString();

    //이미지 저장
    public String imageUploadProcess(MultipartFile file, String realPath) {

        //파일 경로 찾기
        String tempPath = "";

        for(int i=0; i < realPath.indexOf("target"); i++) {
            tempPath += realPath.charAt(i);
        }

//        log.info("mimetype ======> %s", file.getContentType());

        String publicRealPath = tempPath + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "img" + File.separator;

        String originalFileName = file.getOriginalFilename();	//오리지널 파일명


        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자

        String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명


        String clientPath = File.separator + "static" + File.separator + "img" + File.separator + savedFileName;

        try {
            InputStream fis = null;
            fis = file.getInputStream();
            FileOutputStream fos = new FileOutputStream(publicRealPath + savedFileName);

            byte[] buffer = new byte[1024];

            int size = 0;
            while((size = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, size);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clientPath;
    }

    public String s3UploadProcess(MultipartFile file) throws IOException {

        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIASDJ2G6ITZ6B6VQEU", "RyAHVdxwZqwQDUe80svc6WDqjn6fLXeSDbAwLN+T");
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
        //FileInputStream fis = (FileInputStream) file.getInputStream();

        InputStream fis =  new BufferedInputStream(file.getInputStream());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("image/jpeg");

        String fileName = uuid + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = new PutObjectRequest("siktamsik-img", fileName, fis, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);

        String url = "";

        try {
            s3Client.putObject(putObjectRequest);
            // upload success

            url = String.format("https://%s.s3.ap-northeast-2.amazonaws.com/%s", "siktamsik-img", fileName);

            // s3Client.getUrl()
            // update or insert to db ... url ...

        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        return url;
    }
}
