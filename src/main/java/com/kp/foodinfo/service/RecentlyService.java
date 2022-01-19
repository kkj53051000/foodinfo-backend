package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Event;
import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.domain.Issue;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.EventRepository;
import com.kp.foodinfo.repository.FoodRepository;
import com.kp.foodinfo.repository.IssueRepository;
import com.kp.foodinfo.util.StringToDateUtil;
import com.kp.foodinfo.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class RecentlyService {

    private final EventRepository eventRepository;

    private final IssueRepository issueRepository;

    private final FoodRepository foodRepository;

    private final BrandRepository brandRepository;


    public MainUpdateBrandListVo getMainTodayUpdateBrands() {

        Date today = new Date();

        Date date = StringToDateUtil.dateToDateProcess(today);

        List<Brand> brands = brandRepository.findByRecentlyUpdate(date);

        if (brands.size() == 0) {
            new DbNotFoundException();
        }

        if (brands.size() > 3) {
            brands.subList(0, 3);
        }

        return new MainUpdateBrandListVo(brands);
    }


    public MainUpdateBrandListVo getMainRecentlyUpdateBrands() {

        List<Brand> brands = brandRepository.findAll();

        Collections.sort(brands);

        if (brands.size() > 3) {
            brands.subList(0, 3);
        }

        return new MainUpdateBrandListVo(brands);
    }

    public UpdateBrandListVo getTodayUpdateBrands() {

        Date today = new Date();

        Date date = StringToDateUtil.dateToDateProcess(today);

        List<Brand> brands = brandRepository.findByRecentlyUpdate(date);

        if (brands.size() == 0) {
            throw new DbNotFoundException();
        }

        return new UpdateBrandListVo(brands);
    }

    public UpdateBrandListVo getRecentlyUpdateBrands() {
        List<Brand> brands = brandRepository.findAll();

        Collections.sort(brands);

        return new UpdateBrandListVo(brands);
    }

    public MainRecentlyIssueListVo getMainIssueList(String foodName) {

        String lengthChangeTitle = "";

        //Paging Pageable
        Pageable pageRequest = PageRequest.of(0, 5);

        List<MainRecentlyIssueVo> mainRecentlyIssueVos = new ArrayList<>();

        Food food = foodRepository.findByName(foodName).get();


        //issues
        List<Issue> issues = issueRepository.findRecentlyFive(food, pageRequest);

        for (Issue issue : issues) {

            // Title 길이 수정
            if(issue.getTitle().length() > 20) {
                lengthChangeTitle = issue.getTitle().substring(0, 20) + "...";
            }else{
                lengthChangeTitle = issue.getTitle();
            }

            MainRecentlyIssueVo mainRecentlyIssueVo = MainRecentlyIssueVo.builder()
                    .title(lengthChangeTitle)
                    .brandId(issue.getBrand().getId())
                    .brandImg(issue.getBrand().getImg())
                    .startDate(issue.getDate())
                    .build();

            mainRecentlyIssueVos.add(mainRecentlyIssueVo);
        }

        //events
        List<Event> events = eventRepository.findRecentlyFive(food, pageRequest);

        for (Event event : events) {
            // Title 길이 수정
            if(event.getTitle().length() > 20) {
                lengthChangeTitle = event.getTitle().substring(0, 20) + "...";
            }else{
                lengthChangeTitle = event.getTitle();
            }

            MainRecentlyIssueVo mainRecentlyIssueVo = MainRecentlyIssueVo.builder()
                    .title(lengthChangeTitle)
                    .brandId(event.getBrand().getId())
                    .brandImg(event.getBrand().getImg())
                    .startDate(event.getStartDate())
                    .build();

            mainRecentlyIssueVos.add(mainRecentlyIssueVo);
        }

        Collections.sort(mainRecentlyIssueVos);

        return new MainRecentlyIssueListVo(mainRecentlyIssueVos);
    }


    public RecentlyFoodEventIssueListVo getRecentlyEventIssueList(Long foodId) {

        List<RecentlyFoodEventIssueVo> recentlyFoodEventIssueVos = new ArrayList<>();

        Food food = foodRepository.findById(foodId).get();


        //issues
        List<Issue> issues = issueRepository.findRecentlyByFood(food);

        for (Issue issue : issues) {

            RecentlyFoodEventIssueVo recentlyFoodEventIssueVo = RecentlyFoodEventIssueVo.builder()
                    .brandName(issue.getBrand().getName())
                    .brandImg(issue.getBrand().getImg())
                    .eventTypeName(null)
                    .eventTypeImg(null)
                    .title(issue.getTitle())
                    .content(issue.getContent())
                    .img(issue.getImg())
                    .startDate(StringToDateUtil.dateToStringProcess(issue.getDate()))
                    .endDate(null)
                    .type("이슈")
                    .build();


            recentlyFoodEventIssueVos.add(recentlyFoodEventIssueVo);
        }

        //events
        List<Event> events = eventRepository.findRecentlyByFood(food);

        for (Event event : events) {

            RecentlyFoodEventIssueVo recentlyFoodEventIssueVo = RecentlyFoodEventIssueVo.builder()
                    .brandName(event.getBrand().getName())
                    .brandImg(event.getBrand().getImg())
                    .eventTypeName(event.getEventType().getName())
                    .eventTypeImg(event.getEventType().getImg())
                    .title(event.getTitle())
                    .content(event.getContent())
                    .img(event.getImg())
                    .startDate(StringToDateUtil.dateToStringProcess(event.getStartDate()))
                    .endDate(StringToDateUtil.dateToStringProcess(event.getEndDate()))
                    .type("이벤트")
                    .build();

            recentlyFoodEventIssueVos.add(recentlyFoodEventIssueVo);
        }

        Collections.sort(recentlyFoodEventIssueVos);

        return new RecentlyFoodEventIssueListVo(recentlyFoodEventIssueVos);
    }
}
