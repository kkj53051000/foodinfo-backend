package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.*;
import com.kp.foodinfo.dto.FollowDto;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.exception.FollowCheckException;
import com.kp.foodinfo.request.FollowRequest;
import com.kp.foodinfo.repository.*;
import com.kp.foodinfo.util.StringToDateUtil;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.FollowContentVo;
import com.kp.foodinfo.util.FollowContentVoComparator;
import com.kp.foodinfo.vo.IssueEventListVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FollowService {
    private final FollowRepository followRepository;

    private final BrandRepository brandRepository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final IssueService issueService;

    public void saveFollow(FollowDto followDto) {
        log.info("saveFollow() : in");
        log.info("saveFollow() - UserRepository - findById() : run");
        User user = userRepository.findById(followDto.getUser_id())
                .get();

        log.info("saveFollow() - BrandRepository - findById() : run");
        Brand brand = brandRepository.findById(followDto.getBrand_id())
                .get();

        //Follow 여부 체크
        checkFollow(followDto);

        Follow follow = new Follow(user, brand);

        log.info("saveFollow() - FollowRepository - save() : run");
        followRepository.save(follow);
    }

    public BasicVo findFollow(FollowDto followDto) {
        User user = userRepository.findById(followDto.getUser_id())
                .get();

        Brand brand = brandRepository.findById(followDto.getBrand_id())
                .get();


        followRepository.findByUserAndBrand(user, brand)
                .orElseThrow(() -> new DbNotFoundException());

        return new BasicVo("success");
    }

    public void checkFollow(FollowDto followDto) {
        User user = userRepository.findById(followDto.getUser_id())
                .get();

        Brand brand = brandRepository.findById(followDto.getBrand_id())
                .get();

        boolean check =  followRepository.findByUserAndBrand(user, brand)
                .isPresent();

        if(check == true) {
            throw new FollowCheckException();
        }
    }

    public void deleteFollow(FollowDto followDto) {
        log.info("deleteFollow() : in");
        log.info("deleteFollow() - BrandRepository - findById() : run");
        Brand brand = brandRepository.findById(followDto.getBrand_id())
                .get();

        log.info("deleteFollow() - UserRepository - findById() : run");
        User user = userRepository.findById(followDto.getUser_id())
                .get();

        log.info("deleteFollow() - FollowRepository - findByBrandAndUser() : run");
        Follow follow = followRepository.findByBrandAndUser(brand, user)
                .orElseThrow(() -> new DbNotFoundException());

        log.info("deleteFollow() - FollowRepository - delete() : run");
        followRepository.delete(follow);
    }

    //User가 Follow하고있는 브랜드 목록
    public List<Brand> getFollowList(long user_id) {
        log.info("getFollowList() : in");
        log.info("getFollowList() - UserRepository - findById() : run");
        User user = userRepository.findById(user_id)
                .get();

        log.info("getFollowList() - FollowRepository - findByUser() : run");
        List<Follow> follows = followRepository.findByUser(user);

        List<Brand> brands = new ArrayList<>();

        for(int i = 0; i < follows.size(); i++) {
            brands.add(follows.get(i).getBrand());
        }

        log.info("getFollowList() : List<Brand> return");
        return brands;
    }

    //User가 Follow하고있는 브랜드 목록
    public List<Brand> getTimelineFollowList(long user_id) {
        log.info("getFollowList() : in");
        log.info("getFollowList() - UserRepository - findById() : run");
        User user = userRepository.findById(user_id)
                .get();

        log.info("getFollowList() - FollowRepository - findByUser() : run");
        List<Follow> follows = followRepository.findByUser(user);

        List<Brand> brands = new ArrayList<>();

        for(int i = 0; i < follows.size(); i++) {
            brands.add(follows.get(i).getBrand());

            if (brands.size() > 4) {
                break;
            }
        }

        log.info("getFollowList() : List<Brand> return");
        return brands;
    }

    //User가 Follow하고있는 Brand All Event
    public List<FollowContentVo> getFollowAllContentList(long user_id) throws ParseException {
        log.info("getFollowAllContentList() : in");
        log.info("getFollowAllContentList()  - UserRepository - findById() : run");
        User user = userRepository.findById(user_id)
                .get();

        // 브랜드
        log.info("getFollowAllContentList()  - FollowRepository - findByUser() : run");
        List<Follow> follows = followRepository.findByUser(user);


        List<Brand> brands = new ArrayList<>();

        List<FollowContentVo> followContentVos = new ArrayList<>();

        log.info("getFollowAllContentList() : get List<FollowContentVo> for run");
        for(int i = 0; i < follows.size(); i++) {
            //브랜드
            Brand brand = follows.get(i).getBrand();

            brands.add(brand);

            //이슈

            IssueEventListVo issueEventListVo = issueService.getIssueEventList(brand.getId());

            for(int j = 0; j < issueEventListVo.getItems().size(); j++){
                FollowContentVo followContentVo = FollowContentVo.builder()
                        .id(j)
                        .brandName(brand.getName())
                        .brandImg(brand.getImg())
                        .eventTypeName(issueEventListVo.getItems().get(j).getEventTypeName())
                        .eventTypeImg(issueEventListVo.getItems().get(j).getEventTypeImg())
                        .issueTitle(issueEventListVo.getItems().get(j).getTitle())
                        .issueContent(issueEventListVo.getItems().get(j).getContent())
                        .issueImg(issueEventListVo.getItems().get(j).getImg())
                        .issueStartDate(issueEventListVo.getItems().get(j).getStartDateStr())
                        .issueEndDate(issueEventListVo.getItems().get(j).getEndDateStr())
                        .type(issueEventListVo.getItems().get(j).getType())
                        .build();

                followContentVos.add(followContentVo);
            }

//            //이벤트
//            log.info("getFollowAllContentList() - EventRepository - findByBrand() : run");
//            List<Event> events = eventRepository.findByBrand(brand);
//
//            for(int j = 0; j < events.size(); j++){
//
//                Event event = events.get(j);
//
//                FollowContentVo followContentVo = FollowContentVo.builder()
//                        .brandName(brand.getName())
//                        .brandImg(brand.getImg())
//                        .eventTypeName(event.getEventType().getName())
//                        .eventTypeImg(event.getEventType().getImg())
//                        .eventTitle(event.getTitle())
//                        .eventContent(event.getContent())
//                        .eventImg(event.getImg())
//                        .eventStartDate(StringToDateUtil.dateToStringProcess(event.getStartDate()))
//                        .eventEndDate(StringToDateUtil.dateToStringProcess(event.getEndDate()))
//                        .build();
//
//                followContentVos.add(followContentVo);
//            }


        }

        // 객체 날짜별 정렬
        //Collections.sort(followContentVos, new FollowContentVoComparator().reversed());
        log.info("getFollowAllContentList() : List<FollowContentVo> sort");
        Collections.sort(followContentVos);

        log.info("getFollowAllContentList() : List<FollowContentVo> return");
        return followContentVos;
    }





    /*
    //User가 Follow하고있는 Brand Events
    public List<FollowContentVo> getBrandEventContentList(long user_id) {
        User user = userRepository.findById(user_id)
                .get();

        // 브랜드
        List<Follow> follows = followRepository.findByUser(user);

        List<Brand> brands = new ArrayList<>();

        List<FollowContentVo> followContentVos = new ArrayList<>();

        for(int i = 0; i < follows.size(); i++) {
            //브랜드
            Brand brand = follows.get(i).getBrand();

            brands.add(brand);

            //이벤트
            //브랜드 이벤트
            List<BrandEvent> brandEvents = brandEventRepository.findByBrand(brand);

            for(int j = 0; j < brandEvents.size(); j++){
                FollowContentVo followContentVo = new FollowContentVo(brand.getName(), brand.getImg(), "brandEvent", brandEvents.get(j).getTitle(), brandEvents.get(j).getContent(), brandEvents.get(j).getImg(), dateFormat.format(brandEvents.get(j).getStartDate()), dateFormat.format(brandEvents.get(j).getEndDate()));
                followContentVos.add(followContentVo);
            }
        }

        // 객체 날짜별 정렬
        Collections.sort(followContentVos);


        return followContentVos;
    }

    //User가 Follow하고있는 Collab Events
    public List<FollowContentVo> getCollabEventContentList(long user_id) {
        User user = userRepository.findById(user_id)
                .get();

        // 브랜드
        List<Follow> follows = followRepository.findByUser(user);

        List<Brand> brands = new ArrayList<>();

        List<FollowContentVo> followContentVos = new ArrayList<>();

        for(int i = 0; i < follows.size(); i++) {
            //브랜드
            Brand brand = follows.get(i).getBrand();

            brands.add(brand);

            //이벤트
            //콜라보 이벤트
            List<CollabEvent> collabEvents = collabEventRepository.findByBrand(brand);

            for(int j = 0; j < collabEvents.size(); j++){
                FollowContentVo followContentVo = new FollowContentVo(brand.getName(), brand.getImg(), "collabEvent", collabEvents.get(j).getCollabPlatform().getName(), collabEvents.get(j).getTitle(), collabEvents.get(j).getContent(), collabEvents.get(j).getImg(), dateFormat.format(collabEvents.get(j).getStartDate()), dateFormat.format(collabEvents.get(j).getEndDate()));
                followContentVos.add(followContentVo);
            }
        }

        // 객체 날짜별 정렬
        Collections.sort(followContentVos);

        return followContentVos;
    }
     */
}
