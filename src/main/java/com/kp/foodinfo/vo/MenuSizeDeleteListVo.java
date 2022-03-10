package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.MenuSize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class MenuSizeDeleteListVo {

    private List<MenuSizeDeleteVo> items = null;

    public MenuSizeDeleteListVo(List<MenuSizeDeleteVo> menuSizeDeleteVos) {
        this.items = menuSizeDeleteVos;
    }

//    public MenuSizeListVo(List<MenuSize> menuSizes) {
//        items.stream().map(menuSize -> new MenuSizeVo(menuSize))
//    }
//
//    class MenuSizeVo {
//        private long id;
//        private String menuName;
//        private String menuPrice;
//
//        public MenuSizeVo(MenuSize menuSize)  {
//            this.id = menuSize.getId();
//            this.menuName = menuSize.getMenu().getName();
//            this.menuPrice = FormatUtil.menuPriceFormat(menuSize.getMenu().getPrice());
//        }
//    }
}
