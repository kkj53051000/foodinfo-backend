package com.kp.foodinfo.controller;

import com.kp.foodinfo.argumentresolver.Login;
import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.UserSession;
import com.kp.foodinfo.dto.FollowDto;
import com.kp.foodinfo.exception.JwtVerifyFailException;
import com.kp.foodinfo.request.FollowRequest;
import com.kp.foodinfo.service.FollowService;
import com.kp.foodinfo.service.JwtService;
import com.kp.foodinfo.util.ReturnStatus;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.BrandListVo;
import com.kp.foodinfo.vo.FollowContentListVo;
import com.kp.foodinfo.vo.FollowContentVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class FollowController {
    private final FollowService followService;

    // 팔로우
    @PostMapping("/user/followprocess/{id}")
    public BasicVo followProcess(@PathVariable("id") long brand_id, @Login UserSession userSession) {

        FollowDto followDto = new FollowDto(userSession.getUserId(), brand_id);

        followService.saveFollow(followDto);

        BasicVo basicVo = new BasicVo(ReturnStatus.success);

        return basicVo;
    }

    @PostMapping("/user/followcheck/{id}")
    public BasicVo followCheck(@PathVariable("id") long brand_id, @Login UserSession userSession) {
        FollowDto followDto = new FollowDto(userSession.getUserId(), brand_id);

        BasicVo basicVo = followService.findFollow(followDto);

        return basicVo;
    }

    // 팔로우 취소
    @PostMapping("/user/followcancel/{id}")
    public BasicVo followCancel(@PathVariable("id") long brand_id, @Login UserSession userSession) {
        FollowDto followDto = new FollowDto(userSession.getUserId(), brand_id);
        followService.deleteFollow(followDto);

        return new BasicVo("success");
    }

    // 팔로우하고있는 브랜드 리스트
    @GetMapping("/user/followbrandlist")
    public BrandListVo followBrandList(@Login UserSession userSession) {

        List<Brand> brands = followService.getFollowList(userSession.getUserId());

        return new BrandListVo(brands);
    }

    // 타임라인 팔로우하고있는 브랜드 리스트
    @GetMapping("/user/timelinefollowbrandlist")
    public BrandListVo timelineFollowBrandList(@Login UserSession userSession) {
        List<Brand> brands = followService.getTimelineFollowList(userSession.getUserId());

        return new BrandListVo(brands);
    }

    // 팔로우 컨텐츠 리스트 All
    @GetMapping("/user/followallcontentlist/{id}")
    public FollowContentListVo followAllContentList(@Login UserSession userSession, @PathVariable("id") int page) throws ParseException {
        List<FollowContentVo> followContentVos = followService.getFollowAllContentList(userSession.getUserId(), page);

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
