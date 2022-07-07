package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.FoodSpecialCategory;
import com.kp.foodinfo.repository.FoodSpecialCategoryRepository;
import com.kp.foodinfo.request.FoodSpecialCategoryModifyRequest;
import com.kp.foodinfo.request.FoodSpecialCategoryRequest;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodSpecialCategoryService {
    private final FoodSpecialCategoryRepository foodSpecialCategoryRepository;

    private final FileService fileService;

    public BasicVo saveFoodSpecialCategory(
            MultipartFile file,
            FoodSpecialCategoryRequest foodSpecialCategoryRequest) {

        try {
            String clientPath = fileService.s3UploadProcess(file);

            FoodSpecialCategory foodSpecialCategory = FoodSpecialCategory.builder()
                    .name(foodSpecialCategoryRequest.getName())
                    .img(clientPath)
                    .build();

            foodSpecialCategoryRepository.save(foodSpecialCategory);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new BasicVo("success");
    }

    public BasicVo alertFoodSpecialCategory(MultipartFile file, FoodSpecialCategoryModifyRequest foodSpecialCategoryModifyRequest) {
        FoodSpecialCategory foodSpecialCategory = foodSpecialCategoryRepository.findById(foodSpecialCategoryModifyRequest.getId())
                .get();

        // File Change
        if (file != null) {
            try {
                String clientPath = fileService.s3UploadProcess(file);

                foodSpecialCategory.setImg(clientPath);
            } catch (IOException e) {
                return new BasicVo("failure");
            }
        }

        // Name Chage
        if (foodSpecialCategoryModifyRequest.getName() != null) {
            foodSpecialCategory.setName(foodSpecialCategoryModifyRequest.getName());
        }

        foodSpecialCategoryRepository.save(foodSpecialCategory);

        return new BasicVo("success");
    }
}
