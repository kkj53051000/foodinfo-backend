package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.*;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.request.FollowRequest;
import com.kp.foodinfo.repository.*;
import com.kp.foodinfo.vo.FollowContentVo;
import com.kp.foodinfo.util.FollowContentVoComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    private final BrandRepository brandRepository;

    private final UserRepository userRepository;

    private final BrandEventRepository brandEventRepository;

    private final CollabEventRepository collabEventRepository;

    public void saveFollow(FollowRequest followRequest) {
        Brand brand = brandRepository.findById(followRequest.getBrand_id())
                .get();

        User user = userRepository.findById(followRequest.getUser_id())
                .get();

        Follow follow = new Follow(brand, user);

        followRepository.save(follow);
    }

    public void deleteFollow(FollowRequest followForm) {
        Brand brand = brandRepository.findById(followForm.getBrand_id())
                .get();

        User user = userRepository.findById(followForm.getUser_id())
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
    public List<FollowContentVo> getFollowAllContentList(long user_id) {

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
                FollowContentVo followContentVo = new FollowContentVo(brand.getName(), brand.getImg(), "brandEvent", brandEvents.get(j).getTitle(), brandEvents.get(j).getContent(), brandEvents.get(j).getImg(), brandEvents.get(j).getStartDate(), brandEvents.get(j).getEndDate());
                followContentVos.add(followContentVo);
            }

            //콜라보 이벤트
            List<CollabEvent> collabEvents = collabEventRepository.findByBrand(brand);

            for(int j = 0; j < collabEvents.size(); j++){
                FollowContentVo followContentVo = new FollowContentVo(brand.getName(), brand.getImg(), "collabEvent", collabEvents.get(j).getCollabPlatform().getName(), collabEvents.get(j).getTitle(), collabEvents.get(j).getContent(), collabEvents.get(j).getImg(), collabEvents.get(j).getStartDate(), collabEvents.get(j).getEndDate());
                followContentVos.add(followContentVo);
            }
        }

        // 객체 날짜별 정렬
        Collections.sort(followContentVos, new FollowContentVoComparator().reversed());


        return followContentVos;
    }

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
                FollowContentVo followContentVo = new FollowContentVo(brand.getName(), brand.getImg(), "brandEvent", brandEvents.get(j).getTitle(), brandEvents.get(j).getContent(), brandEvents.get(j).getImg(), brandEvents.get(j).getStartDate(), brandEvents.get(j).getEndDate());
                followContentVos.add(followContentVo);
            }
        }

        // 객체 날짜별 정렬
        Collections.sort(followContentVos, new FollowContentVoComparator().reversed());


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
                FollowContentVo followContentVo = new FollowContentVo(brand.getName(), brand.getImg(), "collabEvent", collabEvents.get(j).getCollabPlatform().getName(), collabEvents.get(j).getTitle(), collabEvents.get(j).getContent(), collabEvents.get(j).getImg(), collabEvents.get(j).getStartDate(), collabEvents.get(j).getEndDate());
                followContentVos.add(followContentVo);
            }
        }

        // 객체 날짜별 정렬
        Collections.sort(followContentVos, new FollowContentVoComparator().reversed());

        return followContentVos;
    }
}
