package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.*;
import com.kp.foodinfo.dto.FollowDto;
import com.kp.foodinfo.repository.*;
import com.kp.foodinfo.request.FollowRequest;
import com.kp.foodinfo.util.DateFormatUtil;
import com.kp.foodinfo.vo.FollowContentListVo;
import com.kp.foodinfo.vo.FollowContentVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FollowServiceTest {
    @Autowired
    FollowService followService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    FollowRepository followRepository;

    @Autowired
    EventTypeRepository eventTypeRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    FoodRepository foodRepository;

    @Test
    @Transactional
    void FOLLOW_SAVE_TEST() {
        //given
        //User save
        //회원 인증 UUID 생성
        String uuid = UUID.randomUUID().toString();
        String emailUuid = "test@naver.com" + uuid;

        Date joinDate = new Date();
        User user = new User("test@naver.com", "test", new Date(), DateFormatUtil.dateToStringProcess(new Date()), new Date(), emailUuid, true, Role.USER, false);
        userRepository.save(user);

        //Food save
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        //Brand save
        Brand brand = new Brand("pizzaHut", "test/test.jpg", new Date(), food);
        brandRepository.save(brand);

        //Request
        FollowDto followDto = new FollowDto(user.getId(), brand.getId());

        //when
        followService.saveFollow(followDto);

        //then
        System.out.println("user_id : " + followRepository.findByUser(user).get(0).getId() + " brand_id : " + followRepository.findByUser(user).get(0).getBrand().getId());
        Assertions.assertEquals(followRepository.findByUser(user).size(), 1);
    }

    @Test
    @Transactional
    void FOLLOW_DELETE_TEST() {
        //given
        //User 저장
        //회원 인증 UUID 생성
        String uuid = UUID.randomUUID().toString();
        String emailUuid = "test@naver.com" + uuid;

        Date joinDate = new Date();
        User user = new User("test@naver.com", "test", new Date(), DateFormatUtil.dateToStringProcess(new Date()), new Date(), emailUuid, true, Role.USER, false);
        userRepository.save(user);

        //Food save
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        //Brand 저장
        Brand brand = new Brand("pizzaHut", "test/test.jpg", new Date(), food);
        brandRepository.save(brand);

        //FollowRequest 생성
//        FollowRequest followRequest = new FollowRequest(user.getId(), brand.getId());
        //FollowDto 생성
        FollowDto followDto = new FollowDto(user.getId(), brand.getId());

        //Follow 저장
        followService.saveFollow(followDto);

        //when
        followService.deleteFollow(followDto);

        //then
        Assertions.assertEquals(followRepository.findAll().size(), 0);
    }

    @Test
    @Transactional
    void FOLLOW_GET_LIST_TEST() {
        //given
        //User 저장
        //회원 인증 UUID 생성
        String uuid = UUID.randomUUID().toString();
        String emailUuid = "test@naver.com" + uuid;

        Date joinDate = new Date();
        User user = new User("test@naver.com", "test", new Date(), DateFormatUtil.dateToStringProcess(new Date()), new Date(), emailUuid, true, Role.USER, false);
        userRepository.save(user);

        //Food save
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        //Brand 저장
        Brand brand1 = new Brand("pizzaHut", "test/test.jpg", new Date(), food);
        brandRepository.save(brand1);

        Brand brand2 = new Brand("bbq", "test/bbq.jpg", new Date(), food);
        brandRepository.save(brand2);

        //FollowDto 생성
        FollowDto followDto1 = new FollowDto(user.getId(), brand1.getId());
        FollowDto followDto2 = new FollowDto(user.getId(), brand2.getId());

        //Follow 저장
        followService.saveFollow(followDto1);
        followService.saveFollow(followDto2);

        //when
        List<Brand> brands =followService.getFollowList(user.getId());

        //then
        System.out.println("follow_brand_name_1 : " + brands.get(0).getName() + "follow_brand_name_2 : " + brands.get(1).getName());
        Assertions.assertEquals(brands.size(), 2);
    }

    @Test
    @Transactional
    void FOLLOW_GET_ALL_CONTENT_LIST_TEST() throws ParseException {
        //given
        //User
        //회원 인증 UUID 생성
        String uuid = UUID.randomUUID().toString();
        String emailUuid = "test@naver.com" + uuid;

        User user = new User("test@naver.com", "test", new Date(), DateFormatUtil.dateToStringProcess(new Date()), new Date(), emailUuid, true, Role.USER, false);
        userRepository.save(user);

        //Food save
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        //Brand
        Brand brand1 = new Brand("pizzaHut", "test/test.jpg", new Date(), food);
        Brand brand2 = new Brand("bbq", "/test/test.jpg", new Date(), food);
        brandRepository.save(brand1);
        brandRepository.save(brand2);

        //EventType
        EventType eventType = new EventType("OPO", "/test.jpg");
        eventTypeRepository.save(eventType);

        //Event
        //날짜 변경 (정렬 확인)
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        cal.add(Calendar.MONTH, -2);

        Date pizzaHutEventstartDate = new Date(cal.getTimeInMillis());
        Date pizzaHutEventendDate = new Date();

        cal.setTime(new Date());

        cal.add(Calendar.MONTH, -7);

        Date bbqEventstartDate = new Date(cal.getTimeInMillis());
        Date bbqEventendDate = new Date();

        Event pizzaHutEvent = Event.builder()
                .title("pizza 1 + 1")
                .content("content")
                .img("/test/test.jpg")
                .startDate(pizzaHutEventstartDate)
                .endDate(pizzaHutEventendDate)
                .brand(brand1)
                .eventType(eventType)
                .build();

        Event bbqEvent = Event.builder()
                .title("bbq 1 + 1")
                .content("content")
                .img("/test/test.jpg")
                .startDate(new Date())
                .endDate(new Date())
                .brand(brand1)
                .eventType(eventType)
                .build();

        Event bbqEvent2 = Event.builder()
                .title("bbq 1 + 1010")
                .content("content")
                .img("/test/test.jpg")
                .startDate(bbqEventstartDate)
                .endDate(bbqEventendDate)
                .brand(brand1)
                .eventType(eventType)
                .build();

        eventRepository.save(bbqEvent2);
        eventRepository.save(pizzaHutEvent);
        eventRepository.save(bbqEvent);

        //Follow
        //FollowDto 생성
        FollowDto followDto1 = new FollowDto(user.getId(), brand1.getId());
        FollowDto followDto2 = new FollowDto(user.getId(), brand2.getId());

        //Follow 저장
        followService.saveFollow(followDto1);
        followService.saveFollow(followDto2);

        //when
        // 수정 필요함. 인자
        List<FollowContentVo> followContentVos = followService.getFollowAllContentList(user.getId(), 0);

        //then
        //정렬 잘 됬는지 확인
        for(FollowContentVo f : followContentVos){
            System.out.println("eventTitle : " + f.getIssueTitle());
            System.out.println("eventStartDate : " + f.getIssueStartDate());
        }

        Assertions.assertEquals(followContentVos.size(), 3);
    }

    /*
    @Test
    @Transactional
    void GET_BRAND_EVENT_CONTENT_LIST_TEST() {
        //given
        //User 저장
        Date joinDate = new Date();
        User user = new User("test", "test", "test@naver.com", joinDate, Role.USER);
        userRepository.save(user);

        //Brand 저장
        Brand brand = new Brand("pizzaHut", "test/test.jpg");
        brandRepository.save(brand);

        //BrandEvent 저장
        Date BrandEventstartDate = new Date();
        Date BrandEventendDate = new Date();
        BrandEvent brandEvent = new BrandEvent("brandEvent", "content", "test/test.jpg", BrandEventstartDate, BrandEventendDate, brand);
        brandEventRepository.save(brandEvent);

        //CollabEvent
        //CollabPlatForm 저장
        CollabPlatform collabPlatform = new CollabPlatform("card", "test/card.jpg");
        collabPlatformRepository.save(collabPlatform);

        //CollabEvent 저장
        Date CollabEventstartDate = new Date();
        Date CollabEventendDate = new Date();
        CollabEvent collabEvent = new CollabEvent("collabEvent", "content", "test/test.jpg", CollabEventstartDate, CollabEventendDate, brand, collabPlatform);
        collabEventRepository.save(collabEvent);

        //FollowRequest 생성
        FollowRequest followRequest = new FollowRequest(user.getId(), brand.getId());

        //Follow 저장
        followService.saveFollow(followRequest);

        //when
        List<FollowContentVo> followContentVos = followService.getBrandEventContentList(user.getId());

        //then
        System.out.println("brandEventName : " + followContentVos.get(0).getEventTitle());
        Assertions.assertEquals(followContentVos.size(), 1);
    }

    @Test
    @Transactional
    void FOLLOW_COLLAB_EVENT_CONTENT_LIST_TEST() {
        //given
        //User 저장
        Date joinDate = new Date();
        User user = new User("test", "test", "test@naver.com", joinDate, Role.USER);
        userRepository.save(user);

        //Brand 저장
        Brand brand = new Brand("pizzaHut", "test/test.jpg");
        brandRepository.save(brand);

        //BrandEvent 저장
        Date BrandEventstartDate = new Date();
        Date BrandEventendDate = new Date();
        BrandEvent brandEvent = new BrandEvent("brandEvent", "content", "test/test.jpg", BrandEventstartDate, BrandEventendDate, brand);
        brandEventRepository.save(brandEvent);

        //CollabEvent
        //CollabPlatForm 저장
        CollabPlatform collabPlatform = new CollabPlatform("card", "test/card.jpg");
        collabPlatformRepository.save(collabPlatform);

        //CollabEvent 저장
        Date CollabEventstartDate = new Date();
        Date CollabEventendDate = new Date();
        CollabEvent collabEvent = new CollabEvent("collabEvent", "content", "test/test.jpg", CollabEventstartDate, CollabEventendDate, brand, collabPlatform);
        collabEventRepository.save(collabEvent);

        //FollowRequest 생성
        FollowRequest followRequest = new FollowRequest(user.getId(), brand.getId());

        //Follow 저장
        followService.saveFollow(followRequest);

        //when
        List<FollowContentVo> followContentVos = followService.getCollabEventContentList(user.getId());

        //then
        System.out.println("collabEventName : " + followContentVos.get(0).getEventTitle());
        Assertions.assertEquals(followContentVos.size(), 1);
    }
     */
}