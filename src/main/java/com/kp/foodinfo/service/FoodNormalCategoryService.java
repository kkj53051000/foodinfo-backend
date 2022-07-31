package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.FoodNormalCategory;
import com.kp.foodinfo.repository.FoodNormalCategoryRepository;
import com.kp.foodinfo.request.FoodNormalCategoryModifyRequest;
import com.kp.foodinfo.request.FoodNormalCategoryRemoveRequest;
import com.kp.foodinfo.request.FoodNormalCategoryRequest;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.FoodNormalCategoryAllListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodNormalCategoryService {
    private final FoodNormalCategoryRepository foodNormalCategoryRepository;

    private final FileService fileService;

    public BasicVo saveFoodNormalCategory(MultipartFile file, FoodNormalCategoryRequest foodNormalCategoryRequest) {
        try {

            String clientPath = fileService.s3UploadProcess(file);

            FoodNormalCategory foodNormalCategory = FoodNormalCategory.builder()
                    .name(foodNormalCategoryRequest.getName())
                    .img(clientPath)
                    .build();

            foodNormalCategoryRepository.save(foodNormalCategory);

        } catch (IOException e) {
            e.printStackTrace();
            return new BasicVo("failure");
        }
        return new BasicVo("success");
    }

    public BasicVo alertFoodNormalCategory(MultipartFile file, FoodNormalCategoryModifyRequest foodNormalCategoryModifyRequest) {

        FoodNormalCategory foodNormalCategory = foodNormalCategoryRepository.findById(foodNormalCategoryModifyRequest.getId())
                .get();

        // File Modify
        if (file != null) {
            try {

                String clientPath = fileService.s3UploadProcess(file);

                foodNormalCategory.setImg(clientPath);

            } catch (IOException e) {
                return new BasicVo("failure");
            }

        }

        // Name Modify
        if (foodNormalCategoryModifyRequest.getName() != null) {
            foodNormalCategory.setName(foodNormalCategoryModifyRequest.getName());
        }

        foodNormalCategoryRepository.save(foodNormalCategory);

        return new BasicVo("success");
    }

    public BasicVo deleteFoodNormalCategory(FoodNormalCategoryRemoveRequest foodNormalCategoryRemoveRequest) {
        FoodNormalCategory foodNormalCategory = foodNormalCategoryRepository.findById(foodNormalCategoryRemoveRequest.getId()).get();

        foodNormalCategoryRepository.delete(foodNormalCategory);

        return new BasicVo("success");
    }

    public FoodNormalCategoryAllListVo selectFoodNormalCategoryAll() {

        List<FoodNormalCategory> foodNormalCategoryList = foodNormalCategoryRepository.findAll();

        return new FoodNormalCategoryAllListVo(foodNormalCategoryList);
    }
}
