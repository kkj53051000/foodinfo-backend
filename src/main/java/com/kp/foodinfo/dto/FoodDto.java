package com.kp.foodinfo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@NoArgsConstructor
public class FoodDto {
    private String name;
    private MultipartFile file;

    public FoodDto(String name, MultipartFile file){
        this.name = name;
        this.file = file;
    }
}
