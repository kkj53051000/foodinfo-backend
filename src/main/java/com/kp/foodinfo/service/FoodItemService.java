package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.FoodBrand;
import com.kp.foodinfo.domain.FoodItem;
import com.kp.foodinfo.domain.FoodNormalCategory;
import com.kp.foodinfo.domain.FoodSpecialCategory;
import com.kp.foodinfo.repository.FoodBrandRepository;
import com.kp.foodinfo.repository.FoodItemRepository;
import com.kp.foodinfo.repository.FoodNormalCategoryRepository;
import com.kp.foodinfo.repository.FoodSpecialCategoryRepository;
import com.kp.foodinfo.request.FoodItemRequest;
import com.kp.foodinfo.util.DateFormatUtil;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodItemService {
    private final FoodItemRepository foodItemRepository;

    private final FileService fileService;

    private final FoodBrandRepository foodBrandRepository;

    private final FoodNormalCategoryRepository foodNormalCategoryRepository;

    private final FoodSpecialCategoryRepository foodSpecialCategoryRepository;

    public BasicVo saveFoodItem(MultipartFile file, FoodItemRequest foodItemRequest) {
        try {
            String clientPath = fileService.s3UploadProcess(file);

            FoodBrand foodBrand = foodBrandRepository.findById(foodItemRequest.getFoodBrandId())
                    .get();

            FoodNormalCategory foodNormalCategory = foodNormalCategoryRepository.findById(foodItemRequest.getFoodNormalCategoryId())
                    .get();

            FoodSpecialCategory foodSpecialCategory = foodSpecialCategoryRepository.findById(foodItemRequest.getFoodSpecialCategoryId())
                    .get();

            FoodItem foodItem = FoodItem.builder()
                    .name(foodItemRequest.getName())
                    .img(clientPath)
                    .price(foodItemRequest.getPrice())
                    .date(LocalDate.parse(foodItemRequest.getDate(), DateTimeFormatter.ISO_DATE))
                    .foodBrand(foodBrand)
                    .foodNormalCategory(foodNormalCategory)
                    .foodSpecialCategory(foodSpecialCategory)
                    .build();

            foodItemRepository.save(foodItem);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new BasicVo("success");
    }
}
