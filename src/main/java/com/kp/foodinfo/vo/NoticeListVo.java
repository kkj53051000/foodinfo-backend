package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.Notice;
import com.kp.foodinfo.util.StringToDateUtil;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class NoticeListVo {

    List<NoticeVo> items = null;

    public NoticeListVo(List<Notice> notices) {
        items = notices.stream()
                .map(notice -> new NoticeVo(notice))
                .collect(Collectors.toList());
    }

    @Data
    class NoticeVo {
        private long id;
        private String title;
        private String content;
        private String date;

        public NoticeVo(Notice notice) {
            this.id = notice.getId();
            this.title = notice.getTitle();
            this.content = notice.getContent();
            this.date = StringToDateUtil.dateToStringDayTimeProcess(notice.getDate());
        }
    }
}
