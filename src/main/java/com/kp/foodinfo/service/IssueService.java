package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Event;
import com.kp.foodinfo.domain.Issue;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.EventRepository;
import com.kp.foodinfo.repository.IssueRepository;
import com.kp.foodinfo.request.IssueRequest;
import com.kp.foodinfo.util.ReturnStatus;
import com.kp.foodinfo.util.StringToDateUtil;
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

        Date date = StringToDateUtil.stringToDateProcess(issueRequest.getDateStr());

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

    public IssueListVo getIssueList(long brand_id) {
        Brand brand = brandRepository.findById(brand_id).get();

        List<Issue> issues = issueRepository.findByBrand(brand);

        return new IssueListVo(issues);
    }

    public IssueEventListVo getIssueEventList(long brand_id) {
        Brand brand = brandRepository.findById(brand_id).get();

        List<Event> events = eventRepository.findByBrand(brand);

        List<Issue> issues = issueRepository.findByBrand(brand);

        List<IssueEventVo> issueEventVos = new ArrayList<>();


        // issueEventVos에 events 추가
        for (int i = 0; i < events.size(); i++) {
            IssueEventVo issueEventVo = IssueEventVo.builder()
                    .title(events.get(i).getTitle())
                    .content(events.get(i).getContent())
                    .img(events.get(i).getImg())
                    .startDateStr(StringToDateUtil.dateToStringProcess(events.get(i).getStartDate()))
                    .endDateStr(StringToDateUtil.dateToStringProcess(events.get(i).getEndDate()))
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
                    .startDateStr(StringToDateUtil.dateToStringProcess(issues.get(i).getDate()))
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
