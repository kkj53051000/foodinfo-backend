package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.FoodItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
public class FoodItemListVo {

    private List<FoodItemVo> items = null;

    public FoodItemListVo (List<FoodItem> foodItemList) {
        this.items = foodItemList.stream()
                .map(foodItem -> new FoodItemVo(foodItem))
                .collect(Collectors.toList());
    }

    @Data
    class FoodItemVo {
        private Long id;
        private String name;
        private String img;
        private int price;
        private String date;

        // FoodBrand Info
        private Long foodBrandId;
        private String foodBrandName;
        private String foodBrandImg;

        public FoodItemVo(FoodItem foodItem) {
            this.id = foodItem.getId();
            this.name = foodItem.getName();
            this.img = foodItem.getImg();
            this.price = foodItem.getPrice();
            this.date = foodItem.getDate().toString();
            this.foodBrandId = foodItem.getFoodBrand().getId();
            this.foodBrandName = foodItem.getFoodBrand().getName();
            this.foodBrandImg = foodItem.getFoodBrand().getImg();
        }
    }
}
