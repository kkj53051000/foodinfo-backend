package com.kp.foodinfo.vo;

import com.kp.foodinfo.util.DateFormatUtil;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
public class FollowContentVo implements Comparable<FollowContentVo> {
    private long id;
    private long brandId;
    private String brandName;
    private String brandImg;
    private String eventTypeName;
    private String eventTypeImg;
    private String issueTitle;
    private String issueContent;
    private String issueImg;
    private String issueStartDate;
    private String issueEndDate;
    private String type;

    @Builder
    public FollowContentVo(long id, long brandId, String brandName, String brandImg, String eventTypeName, String eventTypeImg, String issueTitle, String issueContent, String issueImg, String issueStartDate, String issueEndDate, String type) {
        this.id = id;
        this.brandId = brandId;
        this.brandName = brandName;
        this.brandImg = brandImg;
        this.eventTypeName = eventTypeName;
        this.eventTypeImg = eventTypeImg;
        this.issueTitle = issueTitle;
        this.issueContent = issueContent;
        this.issueImg = issueImg;
        this.issueStartDate = issueStartDate;
        this.issueEndDate = issueEndDate;
        this.type = type;
    }

    @Override
    public int compareTo(FollowContentVo f) {
        Date thisDate = DateFormatUtil.stringToDateDayProcess(issueStartDate);
        Date injectDate = DateFormatUtil.stringToDateDayProcess(f.issueStartDate);

        if (injectDate.after(thisDate)) {
            return 1;
        } else if (injectDate.before(thisDate)) {
            return -1;
        }
        return 0;
    }
}