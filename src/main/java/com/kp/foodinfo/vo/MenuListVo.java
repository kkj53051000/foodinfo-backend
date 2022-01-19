package com.kp.foodinfo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kp.foodinfo.domain.Menu;
import com.kp.foodinfo.util.FormatUtil;
import lombok.Builder;
import lombok.Data;

import java.text.DecimalFormat;
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
        private long id;
        private String name;
        private String price;
        private String img;

        public MenuVo(Menu menu) {
            this.id = menu.getId();
            this.name = menu.getName();
            this.price = FormatUtil.menuPriceFormat(menu.getPrice());
            this.img = menu.getImg();
        }
    }
}
