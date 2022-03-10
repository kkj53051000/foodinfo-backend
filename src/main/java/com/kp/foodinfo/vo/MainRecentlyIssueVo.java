package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Getter
public class MainRecentlyIssueVo implements Comparable<MainRecentlyIssueVo> {
    private String title;
    private long brandId;
    private String brandImg;
    private Date startDate;
    private Type type;

    @Override
    public int compareTo(MainRecentlyIssueVo o) {
        return o.getStartDate().compareTo(getStartDate());
    }
}
