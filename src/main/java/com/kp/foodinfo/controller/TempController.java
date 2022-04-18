package com.kp.foodinfo.controller;

import com.kp.foodinfo.service.RecentlyService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TempController {
    // @Autowired
    private final RecentlyService recentlyService;
    private final RedisTemplate redisTemplate;

    public void temp() {

    }
}
