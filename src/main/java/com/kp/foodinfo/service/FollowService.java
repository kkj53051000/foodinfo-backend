package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.*;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.request.FollowRequest;
import com.kp.foodinfo.repository.*;
import com.kp.foodinfo.util.StringToDateUtil;
import com.kp.foodinfo.vo.FollowContentVo;
import com.kp.foodinfo.util.FollowContentVoComparator;
import lombok.RequiredArgsConstructor;
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
public class FollowService {
    private final FollowRepository followRepository;

    private final BrandRepository brandRepository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    public void saveFollow(FollowRequest followRequest) {
        User user = userRepository.findById(followRequest.getUser_id())
                .get();

        Brand brand = brandRepository.findById(followRequest.getBrand_id())
                .get();

        Follow follow = new Follow(user, brand);

        followRepository.save(follow);
    }

    public void deleteFollow(FollowRequest followRequest) {
        Brand brand = brandRepository.findById(followRequest.getBrand_id())
                .get();

        User user = userRepository.findById(followRequest.getUser_id())
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

    //User가 Follow하고있는 Brand All Event
    public List<FollowContentVo> getFollowAllContentList(long user_id) throws ParseException {

        System.out.println("들어옴 !!!!");

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
            List<Event> events = eventRepository.findByBrand(brand);

            for(int j = 0; j < events.size(); j++){
                //FollowContentVo followContentVo = new FollowContentVo(brand.getName(), brand.getImg(), events.get(j).getEventType().getName(), events.get(j).getTitle(), events.get(j).getContent(), events.get(j).getImg(), StringToDateUtil.dateToStringProcess(events.get(j).getStartDate()), StringToDateUtil.dateToStringProcess(events.get(j).getEndDate()));

                Event event = events.get(j);

                FollowContentVo followContentVo = FollowContentVo.builder()
                        .brandName(brand.getName())
                        .brandImg(brand.getImg())
                        .eventTypeName(event.getEventType().getName())
                        .eventTypeImg(event.getEventType().getImg())
                        .eventTitle(event.getTitle())
                        .eventContent(event.getContent())
                        .eventImg(event.getImg())
                        .eventStartDate(StringToDateUtil.dateToStringProcess(event.getStartDate()))
                        .eventEndDate(StringToDateUtil.dateToStringProcess(event.getEndDate()))
                        .build();

                followContentVos.add(followContentVo);
            }
        }

        // 객체 날짜별 정렬
        //Collections.sort(followContentVos, new FollowContentVoComparator().reversed());
        Collections.sort(followContentVos);

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
