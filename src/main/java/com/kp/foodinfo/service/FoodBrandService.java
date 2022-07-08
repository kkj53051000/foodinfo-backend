package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.FoodBrand;
import com.kp.foodinfo.repository.FoodBrandRepository;
import com.kp.foodinfo.request.FoodBrandRequest;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodBrandService {
    private final FoodBrandRepository foodBrandRepository;

    private final FileService fileService;

    public BasicVo saveFoodBrand(MultipartFile file, FoodBrandRequest foodBrandRequest) {
        try {
            String clientPath =  fileService.s3UploadProcess(file);

            FoodBrand foodBrand = FoodBrand.builder()
                    .name(foodBrandRequest.getName())
                    .img(clientPath)
                    .build();

            foodBrandRepository.save(foodBrand);
        } catch (IOException e) {
            e.printStackTrace();
            return new BasicVo("failure");
        }

        return new BasicVo("success");
    }
}
