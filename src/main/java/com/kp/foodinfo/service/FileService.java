package com.kp.foodinfo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class FileService {

    //이미지 저장
    public String imageUploadProcess(MultipartFile file, String realPath) {

        //파일 경로 찾기
        String tempPath = "";

        for(int i=0; i < realPath.indexOf("target"); i++) {
            tempPath += realPath.charAt(i);
        }

        log.info("mimetype ======> %s", file.getContentType());

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
}
