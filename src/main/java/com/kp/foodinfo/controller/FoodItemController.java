package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.request.FoodItemModifyRequest;
import com.kp.foodinfo.request.FoodItemRemoveRequest;
import com.kp.foodinfo.request.FoodItemRequest;
import com.kp.foodinfo.service.FoodItemService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.FoodItemListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FoodItemController {
    private final FoodItemService foodItemService;

    @PostMapping("/admin/fooditemprocess")
    public BasicVo foodItemProcess(
            @RequestPart(name = "file") MultipartFile file,
            @RequestPart(name = "value") FoodItemRequest foodItemRequest) {
        return foodItemService.saveFoodItem(file, foodItemRequest);
    }

    @PostMapping("/admin/fooditemmodify")
    public BasicVo foodItemModify(
            @RequestPart(name = "file", required = false) MultipartFile file,
            @RequestPart FoodItemModifyRequest foodItemModifyRequest) {
        return foodItemService.alertFoodItem(file, foodItemModifyRequest);
    }

    @PostMapping("/admin/fooditemremove")
    public BasicVo foodItemRemove(FoodItemRemoveRequest foodItemRemoveRequest) {
        return foodItemService.deleteFoodItem(foodItemRemoveRequest);
    }

    @GetMapping("/lastndayfooditemlist")
    public FoodItemListVo last1DayFoodItemList(@RequestParam int n) {
        return foodItemService.selectLastNDayFoodItemList(n);
    }

    @GetMapping("/fooditem10list")
    public FoodItemListVo foodItem10List() {
        return foodItemService.selectFoodItem10List();
    }

    // FoodNormalCategory Id에 맞는 FoodItemList
    @GetMapping("/foodnormalcategoryitemlist/{id}")
    public FoodItemListVo foodNormalCategoryItemList(@PathVariable("id") long foodNormalCategoryId) {
        return foodItemService.selectFoodNormalCategoryItemList(foodNormalCategoryId);
    }

    // FoodSpecialCategory Id에 맞는 FoodItemList
    @GetMapping("/foodspecialcategoryitemlist/{id}")
    public FoodItemListVo foodSpecialCategoryItemList(@PathVariable("id") long foodSpecialCategoryId) {

        return foodItemService.selectFoodSpecialCategoryItemList(foodSpecialCategoryId);
    }
}
