package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.FoodNormalCategory;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FoodNormalCategoryAllListVo {

    List<FoodNormalCategoryVo> items = null;

    public FoodNormalCategoryAllListVo(List<FoodNormalCategory> foodNormalCategoryList) {
        this.items = foodNormalCategoryList.stream()
                .map(foodNormalCategory -> new FoodNormalCategoryVo(foodNormalCategory))
                .collect(Collectors.toList());
    }

    @Data
    class FoodNormalCategoryVo {
        private Long id;
        private String name;
        private String img;

        public FoodNormalCategoryVo(FoodNormalCategory foodNormalCategory) {
            this.id = foodNormalCategory.getId();
            this.name = foodNormalCategory.getName();
            this.img = foodNormalCategory.getImg();
        }
    }
}
