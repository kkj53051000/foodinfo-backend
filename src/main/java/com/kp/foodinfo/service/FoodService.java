package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.repository.FoodRepository;
import com.kp.foodinfo.dto.FoodDto;
import com.kp.foodinfo.vo.FoodVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FoodService {
    private final FoodRepository foodRepository;

    private final FileService fileService;

    public void saveFood(FoodDto foodDto) throws IOException {
        MultipartFile file = foodDto.getFile();

        String clientPath = fileService.s3UploadProcess(file);

        Food food = new Food(foodDto.getName(), clientPath);

        foodRepository.save(food);
    }

    public FoodVo getFoodInfo(Long foodId) {
        Food food = foodRepository.findById(foodId).get();

        return new FoodVo(food.getName(), food.getImg());
    }

    public List<Food> getFoodList() {
        log.info("getFoodList() : in");
        log.info("getFoodList() - FoodRepository - findAll() : run");
        List<Food> foods = foodRepository.findAll();

        if (foods.size() == 0) {
            log.error("getFoodList() - DbNotFoundException()");
            throw new DbNotFoundException();
        }

        log.info("getFoodList() : List<Food> return");
        return foods;
    }

//    public void test() {
//        int count = 0;
//        switch (count)
//        case 1:
//            // cache load
//            break   ;
//        case 2:
//            // db load
//        case 3:
//            // cache claer
//    }
}
