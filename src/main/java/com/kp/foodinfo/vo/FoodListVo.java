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

        this.items = foods.stream()
                .map(food -> new FoodVo(food))
                .collect(Collectors.toList());
    }

    @Data
    class FoodVo {
        private long id;
        private String name;
        private String img;

        public FoodVo(Food food) {
            this.id = food.getId();
            this.name = food.getName();
            this.img = food.getImg();
        }
    }
}
