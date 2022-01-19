package com.kp.foodinfo.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class MenuVoList {

    List<MenuVo> items = null;

    public MenuVoList(List<MenuVo> menuVos) {
        this.items = menuVos;
    }
}
