package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.Issue;
import com.kp.foodinfo.util.DateFormatUtil;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class IssueListVo {

    List<IssueVo> items = null;

    public IssueListVo(List<Issue> issues) {
        items = issues.stream()
                .map(issue -> new IssueVo(issue))
                .collect(Collectors.toList());
    }

    @Data
    class IssueVo {

        private long id;
        private String title;
        private String content;
        private String img;
        private String dateStr;

        public IssueVo(Issue issue) {
            this.id = issue.getId();
            this.title = issue.getTitle();
            this.content = issue.getContent();
            this.img = issue.getImg();
            this.dateStr = DateFormatUtil.dateToStringProcess(issue.getDate());
        }
    }
}
