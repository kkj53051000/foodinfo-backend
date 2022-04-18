package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.CollabEvent;
import com.kp.foodinfo.domain.CollabPlatform;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.request.CollabEventRequest;
import com.kp.foodinfo.request.CollabEventListRequest;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.CollabEventRepository;
import com.kp.foodinfo.repository.CollabPlatformRepository;
import com.kp.foodinfo.util.DateFormatUtil;
import com.kp.foodinfo.vo.CollabEventInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CollabEventService {
    private final CollabEventRepository collabEventRepository;

    private final FileService fileService;

    private final BrandRepository brandRepository;

    private final CollabPlatformRepository collabPlatformRepository;

    //콜라보 이벤트 저장
    public void saveCollabEvent(MultipartFile file, CollabEventRequest collabEventRequest, String realPath) {

        String clientPath = fileService.imageUploadProcess(file, realPath);

        Brand brand = brandRepository.findById(collabEventRequest.getBrand_id())
                .get();

        CollabPlatform collabPlatform = collabPlatformRepository.findById(collabEventRequest.getCollabPlatform_id())
                .get();

        // String -> Date
        Date startDate = DateFormatUtil.stringToDateProcess(collabEventRequest.getStartDate() + " " + collabEventRequest.getStartTime());
        Date endDate = DateFormatUtil.stringToDateProcess(collabEventRequest.getEndDate() + " " + collabEventRequest.getEndTime());

        CollabEvent collabEvent = new CollabEvent(collabEventRequest.getTitle(), collabEventRequest.getContent(), clientPath, startDate, endDate, brand, collabPlatform);

        collabEventRepository.save(collabEvent);
    }

    //콜라보 이벤트 (콜라보 플랫폼 종류, 콜라보 플랫폼 컨텐츠 갯수) 가져오기
    public List<CollabEventInfoVo> getCollabEventInfo(long brand_id) {
        List<CollabPlatform> collabPlatforms = collabPlatformRepository.findAll();

        List<CollabEventInfoVo> collabEventInfoVos = new ArrayList<>();

        Brand brand = brandRepository.findById(brand_id)
                .get();


        for (int i = 0; i < collabPlatforms.size(); i++) {

            int count = collabEventRepository.countByBrandAndCollabPlatform(brand, collabPlatforms.get(i));

            CollabEventInfoVo collabEventInfoVo = new CollabEventInfoVo(collabPlatforms.get(i).getName(), collabPlatforms.get(i).getImg(), count);

            collabEventInfoVos.add(collabEventInfoVo);
        }

        return collabEventInfoVos;
    }

    //특정 콜라보 이벤트의 리스트 가져오기
    public List<CollabEvent> getCollabEventList(CollabEventListRequest collabEventListRequest) {
        Brand brand = brandRepository.findById(collabEventListRequest.getBrand_id())
                .get();

        CollabPlatform collabPlatform = collabPlatformRepository.findById(collabEventListRequest.getCollabPlatform_id())
                .get();

        List<CollabEvent> collabEvents = collabEventRepository.findByBrandAndCollabPlatform(brand, collabPlatform);

        if (collabEvents.size() == 0) {
            throw new DbNotFoundException();
        }

        return collabEvents;
    }
}
