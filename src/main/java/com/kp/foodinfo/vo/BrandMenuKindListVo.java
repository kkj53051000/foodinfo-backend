package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.BrandMenuKind;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BrandMenuKindListVo {

    List<BrandMenuKindVo> items = null;

    public BrandMenuKindListVo(List<BrandMenuKind> brandMenuKinds) {
        this.items = brandMenuKinds.stream()
                .map(brandMenuKind -> new BrandMenuKindVo(brandMenuKind))
                .collect(Collectors.toList());
    }

    @Data
    class BrandMenuKindVo {
        private long id;
        private int priority;
        private long brandId;
        private String brandName;

        public BrandMenuKindVo(BrandMenuKind brandMenuKind) {
            this.id = brandMenuKind.getId();
            this.priority = brandMenuKind.getPriority();
            this.brandId = brandMenuKind.getBrand().getId();
            this.brandName = brandMenuKind.getBrand().getName();
        }
    }
}
