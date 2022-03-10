package com.kp.foodinfo.vo;

import lombok.Builder;
import lombok.Data;

@Data
//@NoArgsConstructor
@Builder
public class NoticeVo {
    private long id;
    private String title;
    private String content;
    private String date;

//    public NoticeVo(Notice notice) {
//        this.id = notice.getId();
//        this.title = notice.getTitle();
//        this.content = notice.getContent();
//        this.date = StringToDateUtil.dateToStringDayTimeProcess(notice.getDate());
//    }
}
