package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.request.FollowRequest;
import com.kp.foodinfo.service.FollowService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.BrandListVo;
import com.kp.foodinfo.vo.FollowContentListVo;
import com.kp.foodinfo.vo.FollowContentVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController {
    private final FollowService followService;

    // 팔로우
    @PostMapping("/user/followprocess")
    public BasicVo followProcess(FollowRequest followRequest) {

        followService.saveFollow(followRequest);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    // 팔로우 취소
    @PostMapping("/user/followcancel")
    public BasicVo followCancel(FollowRequest followRequest) {
        followService.deleteFollow(followRequest);

        return new BasicVo("success");
    }

    // 팔로우하고있는 브랜드 리스트
    @PostMapping("/user/followbrandlist")
    public BrandListVo followBrandList(long user_id) {
        List<Brand> brands = followService.getFollowList(user_id);

        return new BrandListVo(brands);
    }

    // 팔로우 컨텐츠 리스트 All
    @PostMapping("/user/followallcontentlist")
    public FollowContentListVo followAllContentList(long user_id) throws ParseException {

        List<FollowContentVo> followContentVos = followService.getFollowAllContentList(user_id);

        return new FollowContentListVo(followContentVos);
    }

    /*
    // 팔로우 브랜드 이벤트 컨텐츠 리스트 (BrandEvent)
    @PostMapping("/user/followbrandeventcontentlist")
    public FollowContentListVo followBrandEventContentList(long user_id) {
        List<FollowContentVo> followContentVos = followService.getBrandEventContentList(user_id);

        return new FollowContentListVo(followContentVos);
    }

    // 팔로우 콜라보 이벤트 컨텐츠 리스트 (CollabEvent)
    @PostMapping("/user/followcollabeventcontentlist")
    public FollowContentListVo followCollabEventContentList(long user_id) {
        List<FollowContentVo> followContentVos = followService.getCollabEventContentList(user_id);

        return new FollowContentListVo(followContentVos);
    }
    */
}
