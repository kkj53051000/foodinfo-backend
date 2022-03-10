package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.*;
import com.kp.foodinfo.dto.FollowDto;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.exception.FollowAllContentEndException;
import com.kp.foodinfo.exception.FollowCheckException;
import com.kp.foodinfo.repository.*;
import com.kp.foodinfo.util.ReturnStatus;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.FollowContentVo;
import com.kp.foodinfo.vo.IssueEventListVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        User user = userRepository.findById(followDto.getUser_id())
                .get();

        Brand brand = brandRepository.findById(followDto.getBrand_id())
                .get();

        //Follow 여부 체크
        checkFollow(followDto);

        Follow follow = new Follow(user, brand);

        followRepository.save(follow);
    }

    public BasicVo findFollow(FollowDto followDto) {
        User user = userRepository.findById(followDto.getUser_id())
                .get();

        Brand brand = brandRepository.findById(followDto.getBrand_id())
                .get();


        followRepository.findByUserAndBrand(user, brand)
                .orElseThrow(() -> new DbNotFoundException());

        return new BasicVo(ReturnStatus.success);
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
        Brand brand = brandRepository.findById(followDto.getBrand_id())
                .get();

        User user = userRepository.findById(followDto.getUser_id())
                .get();

        Follow follow = followRepository.findByBrandAndUser(brand, user)
                .orElseThrow(() -> new DbNotFoundException());

        followRepository.delete(follow);
    }

    //User가 Follow하고있는 브랜드 목록
    public List<Brand> getFollowList(long user_id) {
        User user = userRepository.findById(user_id)
                .get();

        List<Follow> follows = followRepository.findByUser(user);

        List<Brand> brands = new ArrayList<>();

        for(int i = 0; i < follows.size(); i++) {
            brands.add(follows.get(i).getBrand());
        }

        return brands;
    }

    //User가 Follow하고있는 브랜드 목록
    public List<Brand> getTimelineFollowList(long user_id) {

        User user = userRepository.findById(user_id)
                .get();

        List<Follow> follows = followRepository.findByUser(user);

        List<Brand> brands = new ArrayList<>();

        for(int i = 0; i < follows.size(); i++) {
            brands.add(follows.get(i).getBrand());

            if (brands.size() > 4) {
                break;
            }
        }

        return brands;
    }


    /*
    * 3 page ( 10 count )
    * 5 page ( 10 count )
    *
    * 115
    * 104
    * eventId: 111 ( 10 count )
    * issueId: 101 ( 10 count )
    *
    * */

    //User가 Follow하고있는 Brand All Event, Issue
    public List<FollowContentVo> getFollowAllContentList(long user_id, int page) throws ParseException {
        // boolean b = userRepository.existsById(3L);
//        Optional<User> byId = userRepository.findById(3L);
//        if(byId.isPresent()) {
//
//        }


        User user = userRepository.findById(user_id)
                .get();

        // 브랜드
        List<Follow> follows = followRepository.findByUser(user);


        List<Brand> brands = new ArrayList<>();

        List<FollowContentVo> followContentVos = new ArrayList<>();

        /*
        * my brand
        * my brand issue
        * ...page ...
        *
        * select * from issue where brand_id in (0,3,5,10) order by issue.date desc limit 0, 10;
        *
        *
        * [
        * mac issue1
        * mac issue2
        * mac issue3
        * gyo issue1
        * gyo issue2
        * ]
        * */

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


        }

        // 객체 날짜별 정렬
        //Collections.sort(followContentVos, new FollowContentVoComparator().reversed());
        Collections.sort(followContentVos);

        if(followContentVos.size() <= 10){
            return followContentVos;
        }else if(followContentVos.size() > ((page * 10) + 10)){
            int startPage = (page * 10) - 10;
            int endPage = page * 10;

            followContentVos =followContentVos.subList(startPage, endPage);

        }else if(followContentVos.size() > ((page * 10))){
            int startPage = (page * 10) - 10;
            int endPage = (page * 10) + (followContentVos.size() - (page * 10));

            followContentVos = followContentVos.subList(startPage, endPage);
        }else{
            throw new FollowAllContentEndException();
        }

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
