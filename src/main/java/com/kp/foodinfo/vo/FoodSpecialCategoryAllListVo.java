package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.FoodSpecialCategory;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FoodSpecialCategoryAllListVo {

    List<FoodSpecialCategoryVo> items = null;

    public FoodSpecialCategoryAllListVo(List<FoodSpecialCategory> foodSpecialCategoryList) {
        this.items = foodSpecialCategoryList.stream()
                .map(foodSpecialCategory -> new FoodSpecialCategoryVo(foodSpecialCategory))
                .collect(Collectors.toList());
    }

    @Data
    class FoodSpecialCategoryVo {
        private Long id;
        private String name;
        private String img;

        public FoodSpecialCategoryVo(FoodSpecialCategory foodSpecialCategory) {
            this.id = foodSpecialCategory.getId();
            this.name = foodSpecialCategory.getName();
            this.img = foodSpecialCategory.getImg();
        }
    }
}
