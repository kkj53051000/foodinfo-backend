package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.FoodBrand;
import com.kp.foodinfo.repository.FoodBrandRepository;
import com.kp.foodinfo.request.FoodBrandModifyRequest;
import com.kp.foodinfo.request.FoodBrandRemoveRequest;
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

    public BasicVo alertFoodBrand(MultipartFile file, FoodBrandModifyRequest foodBrandModifyRequest) {

        FoodBrand foodBrand = foodBrandRepository.findById(foodBrandModifyRequest.getId())
                .get();

        if (file != null) {
            try {
                String clientPath = fileService.s3UploadProcess(file);

                foodBrand.setImg(clientPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (foodBrandModifyRequest.getName() != null) {
            foodBrand.setName(foodBrandModifyRequest.getName());
        }

        return new BasicVo("success");
    }

    public BasicVo deleteFoodBrand(FoodBrandRemoveRequest foodBrandRemoveRequest) {
        FoodBrand foodBrand = foodBrandRepository.findById(foodBrandRemoveRequest.getId())
                .get();

        foodBrandRepository.delete(foodBrand);

        return new BasicVo("success");
    }
}
