package com.kp.foodinfo.vo;

import com.kp.foodinfo.util.StringToDateUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
public class IssueEventVo implements Comparable<IssueEventVo> {
    private String title;
    private String content;
    private String img;
    private String startDateStr;
    private String endDateStr;
    private String eventTypeName;
    private String eventTypeImg;
    private String type;

    public IssueEventVo(String title, String content, String img, String startDateStr, String endDateStr, String  eventTypeName, String eventTypeImg, String type) {
        this.title = title;
        this.content = content;
        this.img = img;
        this.startDateStr = startDateStr;
        this.endDateStr = endDateStr;
        this.eventTypeName = eventTypeName;
        this.eventTypeImg = eventTypeImg;
        this.type = type;
    }

    @Override
    public int compareTo(IssueEventVo o) {
        return StringToDateUtil.stringToDateDayProcess(o.getStartDateStr()).compareTo(StringToDateUtil.stringToDateDayProcess(getStartDateStr()));
    }
}
