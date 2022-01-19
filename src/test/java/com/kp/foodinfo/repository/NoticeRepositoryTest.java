package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Notice;
import com.kp.foodinfo.domain.Role;
import com.kp.foodinfo.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeRepositoryTest {
    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    public void NOTICE_INSERT_TEST() {
        //given
//        User user = new User("test", "test", "test@naver.com", new Date(), Role.ADMIN);
//        userRepository.save(user);

        Notice notice = new Notice("title", "content", new Date());
        //when
        noticeRepository.save(notice);

        //then
        Assertions.assertNotNull(noticeRepository.findById(notice.getId()));
    }

    @Test
    @Transactional
    public void NOTICE_FIND_BY_TITLE_TEST() {
        //given
//        User user = new User("test", "test", "test@naver.com", new Date(), Role.ADMIN);
//        userRepository.save(user);

        Notice notice = new Notice("title", "content", new Date());
        //when
        noticeRepository.save(notice);

        //then
        Assertions.assertNotNull(noticeRepository.findByTitle(notice.getTitle()).get());
    }
}