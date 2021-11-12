package com.kp.foodinfo.vo;

import lombok.Data;

import java.util.List;

@Data
public class FollowContentListVo {
    private List<FollowContentVo> items = null;

    public FollowContentListVo(List<FollowContentVo> followContentVos) {
        this.items = followContentVos;
    }
}
