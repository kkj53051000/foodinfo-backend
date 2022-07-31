package com.kp.foodinfo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FrIssueRecentlyListVo {
    List<FrIssueRecentlyVo> items;

    public FrIssueRecentlyListVo(List<FrIssueRecentlyVo> frIssueRecentlyVoList) {
        this.items = frIssueRecentlyVoList;
    }
}
