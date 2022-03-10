package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Notice;
import com.kp.foodinfo.repository.NoticeRepository;
import com.kp.foodinfo.request.NoticeModifyRequest;
import com.kp.foodinfo.request.NoticeRequest;
import com.kp.foodinfo.util.ReturnStatus;
import com.kp.foodinfo.util.DateFormatUtil;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.NoticeListVo;
import com.kp.foodinfo.vo.NoticeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public void saveNotice(NoticeRequest noticeRequest) {

        Notice notice = new Notice(noticeRequest.getTitle(), noticeRequest.getContent(), new Date());

        noticeRepository.save(notice);
    }

    public NoticeVo getNotice(long notice_id) {
        Notice notice = noticeRepository.findById(notice_id).get();


        Calendar cal = Calendar.getInstance();

        Date date = notice.getDate();

        cal.setTime(date);
        cal.add(Calendar.HOUR, 9);

        date = cal.getTime();



        NoticeVo noticeVo = NoticeVo.builder()
                .id(notice.getId())
                .content(notice.getContent())
                .title(notice.getTitle())
                .date(DateFormatUtil.dateToStringDayTimeProcess(date))
                .build();

        return noticeVo;
    }

    public NoticeListVo getNoticeList() {
        List<Notice> notices = noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        return new NoticeListVo(notices);
    }

    public BasicVo updateNotice(NoticeModifyRequest noticeModifyRequest) {
        
        Notice notice = noticeRepository.findById(noticeModifyRequest.getNotice_id()).get();

        notice.setTitle(noticeModifyRequest.getTitle());
        notice.setContent(noticeModifyRequest.getContent());

        return new BasicVo(ReturnStatus.success);
    }

    public BasicVo deleteNotice(long notice_id) {
        Notice notice = noticeRepository.findById(notice_id).get();

        noticeRepository.delete(notice);

        return new BasicVo(ReturnStatus.success);
    }
}