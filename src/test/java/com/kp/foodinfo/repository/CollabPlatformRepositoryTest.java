package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.CollabPlatform;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CollabPlatformRepositoryTest {

    @Autowired
    CollabPlatformRepository collabPlatformRepository;

    @Test
    @Transactional
    void COLLAB_PLATFORM_INSERT_TEST() {
        //given
        CollabPlatform collabPlatform = new CollabPlatform("card", "test/test.jpg");

        //when
        collabPlatformRepository.save(collabPlatform);

        //then
        Assertions.assertNotNull(collabPlatformRepository.findById(collabPlatform.getId()).get());
    }

}