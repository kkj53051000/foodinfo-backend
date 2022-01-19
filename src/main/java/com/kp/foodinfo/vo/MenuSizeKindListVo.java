package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.MenuSizeKind;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MenuSizeKindListVo {

    private List<MenuSizeKindVo> items = null;

    public MenuSizeKindListVo(List<MenuSizeKind> menuSizeKinds) {
        this.items = menuSizeKinds.stream()
                .map(menuSizeKind -> new MenuSizeKindVo(menuSizeKind))
                .collect(Collectors.toList());
    }


    @Getter
    class MenuSizeKindVo {
        private Long id;
        private String size;

        public MenuSizeKindVo(MenuSizeKind menuSizeKind) {
            this.id = menuSizeKind.getId();
            this.size = menuSizeKind.getSize();
        }
    }
}
