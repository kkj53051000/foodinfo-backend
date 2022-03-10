package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.Food;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@RedisHash(value = "FoodListVo", timeToLive = 3600)
public class FoodListVo implements Serializable {
    private List<FoodVo> items = null;

    public FoodListVo(List<Food> foods) {

        this.items = foods.stream()
                .map(food -> new FoodVo(food))
                .collect(Collectors.toList());
    }

    @Data
    @RedisHash(value = "FoodVo", timeToLive = 3600)
    class FoodVo implements Serializable {
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
