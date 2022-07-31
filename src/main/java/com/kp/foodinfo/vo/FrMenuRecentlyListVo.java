package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.Menu;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FrMenuRecentlyListVo {

    List<FrMenuRecentlyVo> items = null;

    public FrMenuRecentlyListVo(List<Menu> menuList) {
        this.items = menuList.stream()
                .map(menu -> new FrMenuRecentlyVo(menu))
                .collect(Collectors.toList());
    }

    @Data
    class FrMenuRecentlyVo {
        private Long id;
        private String name;
        private int price;
        private String img;
        private String brandMenuKindName;
        private String brandName;
        private String brandImg;
        private String foodName;

        public FrMenuRecentlyVo(Menu menu) {
            this.id = menu.getId();
            this.name = menu.getName();
            this.price = menu.getPrice();
            this.img = menu.getImg();
            this.brandMenuKindName = menu.getBrandMenuKind().getName();
            this.brandName = menu.getBrandMenuKind().getBrand().getName();
            this.brandImg = menu.getBrandMenuKind().getBrand().getImg();
            this.foodName = menu.getBrandMenuKind().getBrand().getFood().getName();
        }
    }
}
