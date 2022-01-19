package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.domain.Issue;
import com.kp.foodinfo.repository.FoodRepository;
import com.kp.foodinfo.request.MainRecentlyIssueTopTenRequest;
import com.kp.foodinfo.request.RecentlyFoodEventIssueRequest;
import com.kp.foodinfo.service.RecentlyService;
import com.kp.foodinfo.vo.FollowContentVo;
import com.kp.foodinfo.vo.MainRecentlyIssueListVo;
import com.kp.foodinfo.vo.MainUpdateBrandListVo;
import com.kp.foodinfo.vo.UpdateBrandListVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MainController {
    private final RecentlyService recentlyService;

    @GetMapping("/maintodayupdatebrands")
    public MainUpdateBrandListVo mainTodayUpdateBrands() throws ParseException {
        MainUpdateBrandListVo mainUpdateBrandListVo = recentlyService.getMainTodayUpdateBrands();

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
        log.info("mainIssueTopTen() : run");
        MainRecentlyIssueListVo mainRecentlyIssueListVo = recentlyService.getMainIssueList(mainRecentlyIssueTopTenRequest.getFoodName());

        log.info("mainIssueTopTen() : MainRecentlyIssueListVo Return");
        return mainRecentlyIssueListVo;
    }

}
