package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.FoodSpecialCategory;
import com.kp.foodinfo.repository.FoodSpecialCategoryRepository;
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
}
