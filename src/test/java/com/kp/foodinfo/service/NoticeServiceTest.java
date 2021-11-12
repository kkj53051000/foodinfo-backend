package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Role;
import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.repository.NoticeRepository;
import com.kp.foodinfo.repository.UserRepository;
import com.kp.foodinfo.request.NoticeRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeServiceTest {
    @Autowired
    NoticeService noticeService;

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    public void NOTICE_SAVE_TEST() {
        //given
        User user = new User("test", "test", "test@naver.com", new Date(), Role.ADMIN);
        userRepository.save(user);

        NoticeRequest noticeRequest = new NoticeRequest("title", "content");

        //when
        noticeService.saveNotice(noticeRequest, user.getId());

        //then
        Assertions.assertNotNull(noticeRepository.findByTitle(noticeRequest.getTitle()).get());
    }

}