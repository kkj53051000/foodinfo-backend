package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.Notice;
import com.kp.foodinfo.util.StringToDateUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
