package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Notice;
import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.repository.NoticeRepository;
import com.kp.foodinfo.repository.UserRepository;
import com.kp.foodinfo.request.NoticeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    private final UserRepository userRepository;

    public void saveNotice(NoticeRequest noticeRequest, long user_id) {

        User user = userRepository.findById(user_id).get();

        Notice notice = new Notice(noticeRequest.getTitle(), noticeRequest.getContent(), user);

        noticeRepository.save(notice);
    }
}
