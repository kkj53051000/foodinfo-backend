package com.kp.foodinfo.controller;


import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.request.FoodRequest;
import com.kp.foodinfo.request.RecentlyFoodEventIssueRequest;
import com.kp.foodinfo.service.FoodService;
import com.kp.foodinfo.service.RecentlyService;
import com.kp.foodinfo.vo.*;
import com.kp.foodinfo.dto.FoodDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class FoodController {

    private final FoodService foodService;

    private final RecentlyService recentlyService;

    @PostMapping("/admin/foodprocess")
    public BasicVo foodUploadProcess(@RequestPart(value="file", required=true) MultipartFile file, @RequestPart(value="value", required=false) FoodRequest foodRequest) throws IOException {
        log.info("foodUploadProcess() : in");
        FoodDto foodDto = new FoodDto(foodRequest.getName(), file);

        log.info("foodUploadProcess() - FoodService - saveFood() : run");
        foodService.saveFood(foodDto);

        BasicVo basicVo = new BasicVo("success");

        log.info("foodUploadProcess() : BasicVo return");
        return basicVo;
    }

    @GetMapping("/foodlist")
    public FoodListVo foodList(){
        log.info("foodList() : in");
        log.info("foodList() - FoodService - getFoodList() : run");
         List<Food> foodVos = foodService.getFoodList();

        log.info("foodList() : FoodListVo return");
         return new FoodListVo(foodVos);
    }

    @GetMapping("/foodinfo/{id}")
    public FoodVo foodInfo(@PathVariable("id") long foodId) {

        FoodVo foodVo = foodService.getFoodInfo(foodId);

        return foodVo;
    }

    @GetMapping("/recentlyfoodissue/{id}")
    public RecentlyFoodEventIssueListVo recentlyFoodEventIssue(@PathVariable("id") long brandId) {
        RecentlyFoodEventIssueListVo recentlyFoodEventIssueListVo = recentlyService.getRecentlyEventIssueList(brandId);

        return recentlyFoodEventIssueListVo;
    }
}
