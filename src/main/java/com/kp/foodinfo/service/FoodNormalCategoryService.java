package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.FoodNormalCategory;
import com.kp.foodinfo.repository.FoodNormalCategoryRepository;
import com.kp.foodinfo.request.FoodNormalCategoryRequest;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
}
