package com.kp.foodinfo.vo;

import com.kp.foodinfo.util.StringToDateUtil;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
public class FollowContentVo implements Comparable<FollowContentVo>{
    private String brandName;
    private String brandImg;
    private String eventTypeName;
    private String eventTypeImg;
    private String eventTitle;
    private String eventContent;
    private String eventImg;
    private String eventStartDate;
    private String eventEndDate;

    @Builder
    public FollowContentVo(String brandName, String brandImg, String eventTypeName, String eventTypeImg, String eventTitle, String eventContent, String eventImg, String eventStartDate, String eventEndDate) {
        this.brandName = brandName;
        this.brandImg = brandImg;
        this.eventTypeName = eventTypeName;
        this.eventTypeImg = eventTypeImg;
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.eventImg = eventImg;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
    }

    @Override
    public int compareTo(FollowContentVo f) {
        Date thisDate = StringToDateUtil.stringToDateProcess(eventStartDate);
        Date injectDate = StringToDateUtil.stringToDateProcess(f.eventStartDate);

        if(injectDate.after(thisDate)){
            return 1;
        }else if(injectDate.before(thisDate)){
            return -1;
        }
        return 0;
    }
}