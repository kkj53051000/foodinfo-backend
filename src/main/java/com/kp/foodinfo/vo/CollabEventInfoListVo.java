package com.kp.foodinfo.vo;

import lombok.Data;

import java.util.List;

@Data
public class CollabEventInfoListVo {
    private List<CollabEventInfoVo> items = null;

    public CollabEventInfoListVo(List<CollabEventInfoVo> collabEventInfoVos) {
        this.items = collabEventInfoVos;
    }
}
