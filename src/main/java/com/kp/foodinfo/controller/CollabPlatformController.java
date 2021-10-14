package com.kp.foodinfo.controller;

import com.kp.foodinfo.dto.CollabPlatformDto;
import com.kp.foodinfo.service.CollabPlatformService;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class CollabPlatformController {
    private final CollabPlatformService collabPlatformService;

    @PostMapping("/collabplatformprocess")
    public BasicVo collabPlatformProcess(@RequestParam("file") MultipartFile file, @RequestParam String name, HttpServletRequest request) {

        String realPath = request.getServletContext().getRealPath("");

        CollabPlatformDto collabPlatformDto = new CollabPlatformDto(name, file, realPath);

        collabPlatformService.saveCollabPlatform(collabPlatformDto);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    public void test() {

    }
}
