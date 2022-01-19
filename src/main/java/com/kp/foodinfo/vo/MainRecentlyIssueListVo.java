package com.kp.foodinfo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class MainRecentlyIssueListVo {
    List<MainRecentlyIssueVo> items = null;

    public MainRecentlyIssueListVo(List<MainRecentlyIssueVo> items) {
        this.items = items;
    }
}
