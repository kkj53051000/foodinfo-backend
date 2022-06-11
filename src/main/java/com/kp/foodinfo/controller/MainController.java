package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.domain.Issue;
import com.kp.foodinfo.repository.FoodRepository;
import com.kp.foodinfo.request.MainRecentlyIssueTopTenRequest;
import com.kp.foodinfo.request.RecentlyFoodEventIssueRequest;
import com.kp.foodinfo.service.RecentlyService;
import com.kp.foodinfo.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MainController {
    private final RecentlyService recentlyService;
    private final RedisTemplate redisTemplate;


    @GetMapping("/maintodayupdatebrands")
    public MainUpdateBrandListVo mainTodayUpdateBrands() throws ParseException {
        ValueOperations<String, MainUpdateBrandListVo> valueOperations = redisTemplate.opsForValue();

        String redisName = "MainController.mainTodayUpdateBrands()";

        MainUpdateBrandListVo mainUpdateBrandListVo = valueOperations.get(redisName);
        if (mainUpdateBrandListVo == null) {
            mainUpdateBrandListVo = recentlyService.getMainTodayUpdateBrands();
            valueOperations.set(redisName, mainUpdateBrandListVo, 10L, TimeUnit.MINUTES);
        }

        return mainUpdateBrandListVo;
    }

    @GetMapping("/mainrecentlyupdatebrands")
    public MainUpdateBrandListVo mainRecentlyUpdateBrands() {
        MainUpdateBrandListVo mainUpdateBrandListVo = recentlyService.getMainRecentlyUpdateBrands();

        return mainUpdateBrandListVo;
    }

    @GetMapping("/todayupdatebrands")
    public UpdateBrandListVo todayUpdateBrands() throws ParseException {
        UpdateBrandListVo updateBrandListVo = recentlyService.getTodayUpdateBrands();

        return updateBrandListVo;
    }

    @GetMapping("/recentlyupdatebrands")
    public UpdateBrandListVo recentlyUpdateBrands() {
        UpdateBrandListVo updateBrandListVo = recentlyService.getRecentlyUpdateBrands();

        return updateBrandListVo;
    }

    @PostMapping("/mainrecentlyissueten")
    public MainRecentlyIssueListVo mainRecentlyIssueTopTen(@RequestBody MainRecentlyIssueTopTenRequest mainRecentlyIssueTopTenRequest) {
        MainRecentlyIssueListVo mainRecentlyIssueListVo = recentlyService.getMainIssueList(mainRecentlyIssueTopTenRequest.getFoodId());

        return mainRecentlyIssueListVo;
    }


}
