package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.Brand;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MainUpdateBrandListVo implements Serializable {

    List<MainUpdateBrandVo> items = null;

    public MainUpdateBrandListVo(List<Brand> brands) {
        items = brands.stream()
                .map(brand -> new MainUpdateBrandVo(brand))
                .collect(Collectors.toList());
    }

    @Data
    class MainUpdateBrandVo implements Serializable {
        private long id;
        private String img;

        public MainUpdateBrandVo(Brand brand) {
            this.id = brand.getId();
            this.img = brand.getImg();
        }
    }
}
