package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Event;
import com.kp.foodinfo.domain.Issue;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.EventRepository;
import com.kp.foodinfo.repository.IssueRepository;
import com.kp.foodinfo.request.IssueRequest;
import com.kp.foodinfo.util.ReturnStatus;
import com.kp.foodinfo.util.DateFormatUtil;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.IssueEventListVo;
import com.kp.foodinfo.vo.IssueEventVo;
import com.kp.foodinfo.vo.IssueListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;

    private final BrandRepository brandRepository;

    private final FileService fileService;

    private final EventRepository eventRepository;

    public void saveIssue(MultipartFile file, IssueRequest issueRequest) throws IOException {
        Brand brand = brandRepository.findById(issueRequest.getBrand_id()).get();

        String clientPath = fileService.s3UploadProcess(file);

        Date date = DateFormatUtil.stringToDateProcess(issueRequest.getDateStr());

        Issue issue = Issue.builder()
                .title(issueRequest.getTitle())
                .content(issueRequest.getContent())
                .img(clientPath)
                .date(date)
                .brand(brand)
                .build();

        issueRepository.save(issue);

        //Brand RecentlyUpdate 최신화
        brand.setRecentlyUpdate(date);
        brandRepository.save(brand);
    }

    public void saveNoramlIssue(IssueRequest issueRequest) throws IOException {
        Brand brand = brandRepository.findById(issueRequest.getBrand_id()).get();

        Date date = DateFormatUtil.stringToDateProcess(issueRequest.getDateStr());

        Issue issue = Issue.builder()
                .title(issueRequest.getTitle())
                .content(issueRequest.getContent())
                .img(null)
                .date(date)
                .brand(brand)
                .build();

        issueRepository.save(issue);

        //Brand RecentlyUpdate 최신화
        brand.setRecentlyUpdate(date);
        brandRepository.save(brand);
    }

    public IssueListVo getIssueList(long brand_id) {
        Brand brand = brandRepository.findById(brand_id).get();

        List<Issue> issues = issueRepository.findByBrand(brand);

        return new IssueListVo(issues);
    }

    public IssueEventListVo getIssueEventList(long brand_id) {
        Brand brand = brandRepository.findById(brand_id).get();

        // select e from event e where e.id > 111 and e.brand_id in(1,10,100) order by e.date desc limit 10;
        List<Event> events = eventRepository.findByBrand(brand);
        // select e from event e where e.id > 111 and e.brand_id in(1,10,100) order by e.date desc limit 10;
        List<Issue> issues = issueRepository.findByBrand(brand);



        // events 10
        // issues 10


        // 10001    lt, gt, size
        // select * from messages where id < 10001 order by id desc limit 100;
        // select * from messages where id < 9988 order by id desc limit 100;


        // lt, size ( 100, 30 )
        // gt, size ( 101, 50 )


        /*
        * event1
        * issue1
        * issue2
        *
        * event2
        * issue3
        * */

        List<IssueEventVo> issueEventVos = new ArrayList<>();


        // issueEventVos에 events 추가
        for (int i = 0; i < events.size(); i++) {
            IssueEventVo issueEventVo = IssueEventVo.builder()
                    .title(events.get(i).getTitle())
                    .content(events.get(i).getContent())
                    .img(events.get(i).getImg())
                    .startDateStr(DateFormatUtil.dateToStringProcess(events.get(i).getStartDate()))
                    .endDateStr(DateFormatUtil.dateToStringProcess(events.get(i).getEndDate()))
                    .eventTypeName(events.get(i).getEventType().getName())
                    .eventTypeImg(events.get(i).getEventType().getImg())
                    .type("이벤트")
                    .build();

            issueEventVos.add(issueEventVo);
        }

        for (int i = 0; i < issues.size(); i++) {
            IssueEventVo issueEventVo = IssueEventVo.builder()
                    .title(issues.get(i).getTitle())
                    .content(issues.get(i).getContent())
                    .img(issues.get(i).getImg())
                    .startDateStr(DateFormatUtil.dateToStringProcess(issues.get(i).getDate()))
                    .endDateStr(null)
                    .eventTypeName(null)
                    .eventTypeImg(null)
                    .type("이슈")
                    .build();

            issueEventVos.add(issueEventVo);
        }

        Collections.sort(issueEventVos);

//        if (events.size() == 0) {
//            for (int i = 0; i < issues.size(); i++) {
//                IssueEventVo issueEventVo = IssueEventVo.builder()
//                        .title(issues.get(i).getTitle())
//                        .content(issues.get(i).getContent())
//                        .img(issues.get(i).getImg())
//                        .startDateStr(StringToDateUtil.dateToStringProcess(issues.get(i).getDate()))
//                        .endDateStr(null)
//                        .eventTypeName(null)
//                        .eventTypeImg(null)
//                        .type("이슈")
//                        .build();
//
//                issueEventVos.add(issueEventVo);
//            }
//        }else{
//            //issueEventVos사이에 issues 추가 (날짜 오름차순)
//            for (int i = 0; i < issues.size(); i++) {
//                for (int j = 0; j < issueEventVos.size(); j++) {
//                    Date startDate = StringToDateUtil.stringToDateDayProcess(issueEventVos.get(j).getStartDateStr());
//
//                    if (startDate.before(issues.get(i).getDate()) || startDate.equals(issues.get(i).getDate())) {
//                        IssueEventVo issueEventVo = IssueEventVo.builder()
//                                .title(issues.get(i).getTitle())
//                                .content(issues.get(i).getContent())
//                                .img(issues.get(i).getImg())
//                                .startDateStr(StringToDateUtil.dateToStringProcess(issues.get(i).getDate()))
//                                .endDateStr(null)
//                                .eventTypeName(null)
//                                .eventTypeImg(null)
//                                .type("이슈")
//                                .build();
//
//                        issueEventVos.add(j, issueEventVo);
//
//                        break;
//                    }
//                }
//            }
//        }

        return new IssueEventListVo(issueEventVos);
    }

    public BasicVo deleteIssue(long issue_id) {
        Issue issue = issueRepository.findById(issue_id).get();

        issueRepository.delete(issue);

        return new BasicVo(ReturnStatus.success);
    }
}
