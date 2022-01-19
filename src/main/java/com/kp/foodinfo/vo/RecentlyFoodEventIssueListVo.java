package com.kp.foodinfo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecentlyFoodEventIssueListVo {
    List<RecentlyFoodEventIssueVo> items = null;

    public RecentlyFoodEventIssueListVo(List<RecentlyFoodEventIssueVo> recentlyFoodEventIssueVos) {
        this.items = recentlyFoodEventIssueVos;
    }
}
