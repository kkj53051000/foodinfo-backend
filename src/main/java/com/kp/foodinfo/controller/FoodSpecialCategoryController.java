package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.FoodSpecialCategoryModifyRequest;
import com.kp.foodinfo.request.FoodSpecialCategoryRemoveRequest;
import com.kp.foodinfo.request.FoodSpecialCategoryRequest;
import com.kp.foodinfo.service.FoodSpecialCategoryService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.FoodSpecialCategoryAllListVo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FoodSpecialCategoryController {
    private final FoodSpecialCategoryService foodSpecialCategoryService;

    @PostMapping("/admin/foodspecialcategoryprocess")
    public BasicVo foodSpecialCategoryProcess(
            @RequestPart(name = "file") MultipartFile file,
            @RequestPart(name = "value") FoodSpecialCategoryRequest foodSpecialCategoryRequest) {

        return foodSpecialCategoryService.saveFoodSpecialCategory(file, foodSpecialCategoryRequest);
    }

    @PostMapping("/admin/foodspecialcategorymodify")
    public BasicVo foodSpecialCategoryModify(
            @RequestPart(name = "file", required = false) MultipartFile file,
            @RequestPart(name = "value") FoodSpecialCategoryModifyRequest foodSpecialCategoryModifyRequest) {

        return foodSpecialCategoryService.alertFoodSpecialCategory(file, foodSpecialCategoryModifyRequest);
    }

    @PostMapping("/admin/foodspecialcategoryremove")
    public BasicVo foodSpecialCategoryRemove(@RequestBody FoodSpecialCategoryRemoveRequest foodSpecialCategoryRemoveRequest) {
        return foodSpecialCategoryService.deleteFoodSpecialCategory(foodSpecialCategoryRemoveRequest);
    }

    @GetMapping("/foodspecialcategoryalllist")
    public FoodSpecialCategoryAllListVo foodSpecialCategoryAllList() {
        return foodSpecialCategoryService.selectFoodSpecialCategoryAll();
    }
}
