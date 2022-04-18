package com.kp.foodinfo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class BrandDto {
    private String name;
    private MultipartFile file;
    private long food_id;

    public BrandDto(String name, MultipartFile file, long food_id) {
        this.name = name;
        this.file = file;
        this.food_id = food_id;
    }
}
