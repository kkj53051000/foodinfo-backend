package com.kp.foodinfo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@NoArgsConstructor
public class CollabPlatformDto {
    private String name;
    private MultipartFile file;

    public CollabPlatformDto(String name, MultipartFile file) {
        this.name = name;
        this.file = file;
    }
}
