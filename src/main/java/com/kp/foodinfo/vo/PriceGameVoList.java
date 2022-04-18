package com.kp.foodinfo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;

@NoArgsConstructor
@Data
public class PriceGameVoList {
    List<PriceGameVo> items = null;

    public PriceGameVoList(List<PriceGameVo> priceGameVos) {
        this.items = priceGameVos;
    }
}
