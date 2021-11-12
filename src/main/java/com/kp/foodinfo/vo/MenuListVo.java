package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.Menu;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MenuListVo {
    List<MenuVo> items = null;

    public MenuListVo(List<Menu> menus) {
        items = menus.stream()
                .map(menu -> new MenuVo(menu))
                .collect(Collectors.toList());
    }

    @Data
    class MenuVo {
        private String name;
        private int price;
        private String img;

        public MenuVo(Menu menu) {
            this.name = menu.getName();
            this.price = menu.getPrice();
            this.img = menu.getImg();
        }
    }
}
