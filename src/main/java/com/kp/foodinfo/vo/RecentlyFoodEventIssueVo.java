package com.kp.foodinfo.vo;

import com.kp.foodinfo.util.StringToDateUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class RecentlyFoodEventIssueVo implements Comparable<RecentlyFoodEventIssueVo> {
    private long id;
    private String brandName;
    private String brandImg;
    private String eventTypeName;
    private String eventTypeImg;
    private String title;
    private String content;
    private String img;
    private String startDate;
    private String endDate;
    private String type;


    public RecentlyFoodEventIssueVo(long id, String brandName, String brandImg, String eventTypeName, String eventTypeImg, String title, String content, String img, String startDate, String endDate, String type) {
        this.id = id;
        this.brandName = brandName;
        this.brandImg = brandImg;
        this.eventTypeName = eventTypeName;
        this.eventTypeImg = eventTypeImg;
        this.title = title;
        this.content = content;
        this.img = img;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    @Override
    public int compareTo(RecentlyFoodEventIssueVo o) {
        return StringToDateUtil.stringToDateDayProcess(o.getStartDate()).compareTo(StringToDateUtil.stringToDateDayProcess(getStartDate()));
    }
}
