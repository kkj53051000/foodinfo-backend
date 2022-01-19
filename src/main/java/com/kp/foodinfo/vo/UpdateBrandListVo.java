package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.Brand;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UpdateBrandListVo {

    List<UpdateBrandVo> items = null;

    public UpdateBrandListVo(List<Brand> brands) {
        items = brands.stream()
                .map(brand -> new UpdateBrandVo(brand))
                .collect(Collectors.toList());
    }


    @Data
    class UpdateBrandVo {
        private long id;
        private String name;
        private String img;

        public UpdateBrandVo(Brand brand) {
            this.id = brand.getId();
            this.name = brand.getName();
            this.img = brand.getImg();
        }
    }
}
