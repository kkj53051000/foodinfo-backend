package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.FoodBrand;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FoodBrandAllListVo {

    List<FoodBrandVo> items = null;

    public FoodBrandAllListVo(List<FoodBrand> foodBrandList) {
        this.items = foodBrandList.stream()
                .map(foodBrand -> new FoodBrandVo(foodBrand))
                .collect(Collectors.toList());
    }

    class FoodBrandVo {
        private Long id;
        private String name;
        private String img;

        public FoodBrandVo(FoodBrand foodBrand) {
            this.id = foodBrand.getId();
            this.name = foodBrand.getName();
            this.img = foodBrand.getImg();
        }
    }
}
