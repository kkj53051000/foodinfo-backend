package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.request.FollowRequest;
import com.kp.foodinfo.service.FollowService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.FollowContentVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    // 팔로우
    @PostMapping("/followprocess")
    public BasicVo followProcess(FollowRequest followRequest) {

        followService.saveFollow(followRequest);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    // 팔로우 취소
    @PostMapping("/followcancel")
    public BasicVo followCancel(@RequestParam FollowRequest followForm) {
        followService.deleteFollow(followForm);

        return new BasicVo("success");
    }

    // 팔로우 브랜드 리스트
    @PostMapping("/followbrandlist")
    public List<Brand> followList(@RequestParam long user_id) {
        List<Brand> brands = followService.getFollowList(user_id);

        return brands;
    }

    // 팔로우 컨텐츠 리스트 (브랜드, 이벤트 내용, 기본 이벤트 and 콜라보 이벤트)
    @PostMapping("/followallcontentlist")
    public List<FollowContentVo> followAllContentList(@RequestParam long user_id) {

        List<FollowContentVo> followContentVos = followService.getFollowAllContentList(user_id);

        return followContentVos;
    }

    // 팔로우 브랜드 이벤트 컨텐츠 리스트 (BrandEvent)
    @PostMapping("/followbrandeventcontentlist")
    public List<FollowContentVo> followBrandEventContentList(@RequestParam long user_id) {
        List<FollowContentVo> followContentVos = followService.getBrandEventContentList(user_id);

        return followContentVos;
    }

    // 팔로우 콜라보 이벤트 컨텐츠 리스트 (CollabEvent)
    @PostMapping("/followcollabeventcontentlist")
    public List<FollowContentVo> followCollabEventContentList(long user_id) {
        List<FollowContentVo> followContentVos = followService.getCollabEventContentList(user_id);

        return followContentVos;
    }
}
