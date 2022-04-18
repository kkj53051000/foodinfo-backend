package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.*;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.EventRepository;
import com.kp.foodinfo.repository.FoodRepository;
import com.kp.foodinfo.repository.IssueRepository;
import com.kp.foodinfo.util.DateFormatUtil;
import com.kp.foodinfo.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Date date = DateFormatUtil.dateToDateProcess(today);

        List<Brand> brands = brandRepository.findByRecentlyUpdate(date);

        int brandsSize = brands.size();

        if (brandsSize == 0) {
            new DbNotFoundException();
        } else if (brandsSize > 3) {
            brands = brands.subList(0, 4);
        }

        return new MainUpdateBrandListVo(brands);
    }


    public MainUpdateBrandListVo getMainRecentlyUpdateBrands() {

        List<Brand> brands = brandRepository.findAll();

        Collections.sort(brands);

        if (brands.size() > 3) {
            brands = brands.subList(0, 4);
        }

        return new MainUpdateBrandListVo(brands);
    }

    public UpdateBrandListVo getTodayUpdateBrands() {

        Date today = new Date();

        Date date = DateFormatUtil.dateToDateProcess(today);

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

    public MainRecentlyIssueListVo getMainIssueList(Long FoodId) {

        String lengthChangeTitle = "";

        //Paging Pageable
        Pageable pageRequest = PageRequest.of(0, 5);

        List<MainRecentlyIssueVo> mainRecentlyIssueVos = new ArrayList<>();

        Food food = foodRepository.findById(FoodId).orElseThrow(() -> new DbNotFoundException());


        //issues
        List<Issue> issues = issueRepository.findByBrand_Food(food);

        for (Issue issue : issues) {

            // Title 길이 수정
            if (issue.getTitle().length() > 15) {
                lengthChangeTitle = issue.getTitle().substring(0, 15) + "...";
            } else {
                lengthChangeTitle = issue.getTitle();
            }

            MainRecentlyIssueVo mainRecentlyIssueVo = MainRecentlyIssueVo.builder()
                    .title(lengthChangeTitle)
                    .brandId(issue.getBrand().getId())
                    .brandImg(issue.getBrand().getImg())
                    .startDate(issue.getDate())
                    .type(Type.ISSUE)
                    .build();

            mainRecentlyIssueVos.add(mainRecentlyIssueVo);
        }

        //events
        List<Event> events = eventRepository.findByBrand_FoodAndEndDateGreaterThanEqual(food, DateFormatUtil.dateToDateProcess(new Date()));

        for (Event event : events) {
            // Title 길이 수정
            if (event.getTitle().length() > 15) {
                lengthChangeTitle = event.getTitle().substring(0, 15) + "...";
            } else {
                lengthChangeTitle = event.getTitle();
            }

            MainRecentlyIssueVo mainRecentlyIssueVo = MainRecentlyIssueVo.builder()
                    .title(lengthChangeTitle)
                    .brandId(event.getBrand().getId())
                    .brandImg(event.getBrand().getImg())
                    .startDate(event.getStartDate())
                    .type(Type.EVENT)
                    .build();

            mainRecentlyIssueVos.add(mainRecentlyIssueVo);
        }

        if (mainRecentlyIssueVos.size() == 0) {
            throw new DbNotFoundException();
        } else if (mainRecentlyIssueVos.size() > 10) {
            Collections.sort(mainRecentlyIssueVos);
            mainRecentlyIssueVos = mainRecentlyIssueVos.subList(0, 10);
        } else {
            Collections.sort(mainRecentlyIssueVos);
        }


        return new MainRecentlyIssueListVo(mainRecentlyIssueVos);
    }


    public RecentlyFoodEventIssueListVo getRecentlyEventIssueList(Long foodId) {

        List<RecentlyFoodEventIssueVo> recentlyFoodEventIssueVos = new ArrayList<>();
        long recentlyFoodEventIssueId = 0;

        Food food = foodRepository.findById(foodId).get();


        //issues
        List<Issue> issues = issueRepository.findRecentlyByFood(food);

        for (Issue issue : issues) {

            RecentlyFoodEventIssueVo recentlyFoodEventIssueVo = RecentlyFoodEventIssueVo.builder()
                    .id(recentlyFoodEventIssueId)
                    .brandId(issue.getBrand().getId())
                    .brandName(issue.getBrand().getName())
                    .brandImg(issue.getBrand().getImg())
                    .eventTypeName(null)
                    .eventTypeImg(null)
                    .title(issue.getTitle())
                    .content(issue.getContent())
                    .img(issue.getImg())
                    .startDate(DateFormatUtil.dateToStringProcess(issue.getDate()))
                    .endDate(null)
                    .type("이슈")
                    .build();

            recentlyFoodEventIssueVos.add(recentlyFoodEventIssueVo);
            recentlyFoodEventIssueId += 1;
        }

        //events
        List<Event> events = eventRepository.findByBrand_FoodAndEndDateGreaterThanEqual(food, DateFormatUtil.dateToDateProcess(new Date()));

        for (Event event : events) {

            RecentlyFoodEventIssueVo recentlyFoodEventIssueVo = RecentlyFoodEventIssueVo.builder()
                    .id(recentlyFoodEventIssueId)
                    .brandId(event.getBrand().getId())
                    .brandName(event.getBrand().getName())
                    .brandImg(event.getBrand().getImg())
                    .eventTypeName(event.getEventType().getName())
                    .eventTypeImg(event.getEventType().getImg())
                    .title(event.getTitle())
                    .content(event.getContent())
                    .img(event.getImg())
                    .startDate(DateFormatUtil.dateToStringProcess(event.getStartDate()))
                    .endDate(DateFormatUtil.dateToStringProcess(event.getEndDate()))
                    .type("이벤트")
                    .build();

            recentlyFoodEventIssueVos.add(recentlyFoodEventIssueVo);
            recentlyFoodEventIssueId += 1;
        }

        Collections.sort(recentlyFoodEventIssueVos);

        return new RecentlyFoodEventIssueListVo(recentlyFoodEventIssueVos);
    }
}
