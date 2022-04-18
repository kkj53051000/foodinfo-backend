package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.BrandEvent;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BrandEventListVo {
    List<BrandEventVo> items = null;

    public BrandEventListVo(List<BrandEvent> brandEvents) {
        this.items = brandEvents.stream()
                .map(brandEvent -> new BrandEventVo(brandEvent))
                .collect(Collectors.toList());
    }

    @Data
    class BrandEventVo {
        private String title;
        private String content;
        private String img;
        private Date startDate;
        private Date endDate;
        private long brand_id;

        public BrandEventVo(BrandEvent brandEvent) {
            this.title = brandEvent.getTitle();
            this.content = brandEvent.getContent();
            this.img = brandEvent.getImg();
            this.startDate = brandEvent.getStartDate();
            this.endDate = brandEvent.getEndDate();
            this.brand_id = brandEvent.getBrand().getId();
        }
    }
}
