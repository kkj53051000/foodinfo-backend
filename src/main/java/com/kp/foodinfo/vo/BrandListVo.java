package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.Brand;

import java.util.List;
import java.util.stream.Collectors;

public class BrandListVo {

    private List<BrandVo> items = null;

    public BrandListVo(List<Brand> brands){
        this.items = brands.stream()
                .map(brand -> new BrandVo(brand))
                .collect(Collectors.toList());
    }

    class BrandVo {
        private String name;
        private String img;

        public BrandVo(Brand brand) {
            this.name = brand.getName();
            this.img = brand.getImg();
        }
    }
}
