package com.kp.foodinfo.vo;

import com.kp.foodinfo.util.FormatUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class MenuSizeVo implements Serializable {
    private String size;
    private String price;

    public MenuSizeVo(String size, int price) {
        this.size = size;
        this.price = FormatUtil.menuPriceFormat(price);
    }
}
