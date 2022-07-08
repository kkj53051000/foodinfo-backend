package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.FoodItemModifyRequest;
import com.kp.foodinfo.request.FoodItemRequest;
import com.kp.foodinfo.service.FoodItemService;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FoodItemController {
    private final FoodItemService foodItemService;

    @PostMapping("/admin/fooditemprocess")
    public BasicVo foodItemProcess(
            @RequestPart(name = "file") MultipartFile file,
            @RequestPart FoodItemRequest foodItemRequest) {
        return foodItemService.saveFoodItem(file, foodItemRequest);
    }

    @PostMapping("/admin/fooditemmodify")
    public BasicVo foodItemModify(
            @RequestPart(name = "file", required = false) MultipartFile file,
            @RequestPart FoodItemModifyRequest foodItemModifyRequest) {
        return foodItemService.alertFoodItem(file, foodItemModifyRequest);
    }
}
