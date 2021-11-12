package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.repository.FoodRepository;
import com.kp.foodinfo.dto.FoodDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    private final FileService fileService;

    public void saveFood(FoodDto foodDto, String realPath) {
        MultipartFile file = foodDto.getFile();

        String clientPath = fileService.imageUploadProcess(file, realPath);

        System.out.println("clientPath : " + clientPath);

        Food food = new Food(foodDto.getName(), clientPath);

        foodRepository.save(food);
    }

    public List<Food> getFoodList(){
        List<Food> foods = foodRepository.findAll();

        if(foods.size() == 0){
            throw new DbNotFoundException();
        }

        return foods;
    }
}
