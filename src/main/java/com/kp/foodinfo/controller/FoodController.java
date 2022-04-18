package com.kp.foodinfo.controller;


import com.kp.foodinfo.aop.LogExcutionTime;
import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.request.FoodRequest;
import com.kp.foodinfo.request.RecentlyFoodEventIssueRequest;
import com.kp.foodinfo.service.FoodService;
import com.kp.foodinfo.service.RecentlyService;
import com.kp.foodinfo.vo.*;
import com.kp.foodinfo.dto.FoodDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class FoodController {

    private final FoodService foodService;

    private final RecentlyService recentlyService;

    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping("/admin/foodprocess")
    public BasicVo foodUploadProcess(@RequestPart(value = "file", required = true) MultipartFile file, @RequestPart(value = "value", required = false) FoodRequest foodRequest) throws IOException {
        FoodDto foodDto = new FoodDto(foodRequest.getName(), file);

        foodService.saveFood(foodDto);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    @GetMapping("/foodlist")
    public FoodListVo foodList() {
        ValueOperations<String, FoodListVo> valueOperations = redisTemplate.opsForValue();

        FoodListVo foodListVo = valueOperations.get("FoodController.foodList()");

        if (foodListVo == null) {
            List<Food> foodVos = foodService.getFoodList();

            foodListVo = new FoodListVo(foodVos);
            valueOperations.set("FoodController.foodList()", foodListVo, 2L, TimeUnit.HOURS);
        }

        return foodListVo;
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
