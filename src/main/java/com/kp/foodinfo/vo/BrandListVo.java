package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.Brand;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BrandListVo {

    private List<BrandVo> items = null;

    public BrandListVo(List<Brand> brands){
        this.items = brands.stream()
                .map(brand -> new BrandVo(brand))
                .collect(Collectors.toList());
    }

    @Data
    class BrandVo {
        private long brand_id;
        private String name;
        private String img;

        public BrandVo(Brand brand) {
            this.brand_id = brand.getId();
            this.name = brand.getName();
            this.img = brand.getImg();
        }
    }
}
