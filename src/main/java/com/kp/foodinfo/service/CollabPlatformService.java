package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.CollabPlatform;
import com.kp.foodinfo.dto.CollabPlatformDto;
import com.kp.foodinfo.repository.CollabPlatformRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class CollabPlatformService {
    private final CollabPlatformRepository collabPlatformRepository;

    private final FileService fileService;


    public void saveCollabPlatform(CollabPlatformDto collabPlatformDto) throws IOException {

        String clientPath = fileService.s3UploadProcess(collabPlatformDto.getFile());

        CollabPlatform collabPlatform = new CollabPlatform(collabPlatformDto.getName(), clientPath);

        collabPlatformRepository.save(collabPlatform);
    }
}
