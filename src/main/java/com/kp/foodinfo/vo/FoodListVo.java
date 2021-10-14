package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.Food;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FoodListVo {
    private List<FoodVo> items = null;

    public FoodListVo(List<Food> foods) {
//        List<FoodVo> foodVoList = new ArrayList<>();
//
//        for(Food food : foods) {
//            foodVoList.add(new FoodVo(food));
//        }
//
//        this.items = foodVoList;

        this.items = foods.stream()
                .map(food -> new FoodVo(food))
                .collect(Collectors.toList());
    }

    @Data
    class FoodVo {
        private String name;
        private String img;

        public FoodVo(Food food) {
            this.name = food.getName();
            this.img = food.getImg();
        }
    }
}
