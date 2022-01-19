package com.kp.foodinfo.vo;

import lombok.Data;

import java.util.List;

@Data
public class IssueEventListVo {

    List<IssueEventVo> items = null;

    public IssueEventListVo(List<IssueEventVo> issueEventVos) {
        items = issueEventVos;
    }
}
