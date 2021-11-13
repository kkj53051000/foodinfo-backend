package com.kp.foodinfo.controller;


import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.service.FoodService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.dto.FoodDto;
import com.kp.foodinfo.vo.FoodListVo;
import com.kp.foodinfo.vo.FoodVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FoodController {

    private final FoodService foodService;

    @PostMapping("/admin/foodprocess")
    public BasicVo foodUploadProcess(MultipartFile file, String name) throws IOException {

        FoodDto foodDto = new FoodDto(name, file);

        foodService.saveFood(foodDto);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    @PostMapping("/foodlist")
    public FoodListVo foodList(){

         List<Food> foodVos = foodService.getFoodList();

         return new FoodListVo(foodVos);
    }
}
