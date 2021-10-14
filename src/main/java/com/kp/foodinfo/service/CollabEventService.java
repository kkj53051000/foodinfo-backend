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
import com.kp.foodinfo.vo.CollabEventMenuListVo;
import com.kp.foodinfo.vo.CollabEventMenuVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

        CollabEvent collabEvent = new CollabEvent(collabEventRequest.getTitle(), collabEventRequest.getContent(), clientPath, collabEventRequest.getStartDate(), collabEventRequest.getEndDate(), brand, collabPlatform);

        collabEventRepository.save(collabEvent);
    }

    //콜라보 이벤트 (콜라보 플랫폼 종류, 콜라보 플랫폼 컨텐츠 갯수) 가져오기
    public List<CollabEventMenuVo> getCollabEventMenu(long brand_id) {
         List<CollabPlatform> collabPlatforms = collabPlatformRepository.findAll();

         List<CollabEventMenuVo> collabEventMenuVos = new ArrayList<>();

         Brand brand = brandRepository.findById(brand_id)
                 .get();


         for(int i = 0; i < collabPlatforms.size(); i++) {

             int count = collabEventRepository.countByBrandAndCollabPlatform(brand, collabPlatforms.get(i));

             CollabEventMenuVo collabEventMenuVo = new CollabEventMenuVo(collabPlatforms.get(i).getName(), collabPlatforms.get(i).getImg(), count);

             collabEventMenuVos.add(collabEventMenuVo);
         }

         return collabEventMenuVos;
    }

    //특정 콜라보 이벤트의 리스트 가져오기
    public List<CollabEvent> getCollabEventList(CollabEventListRequest collabEventListRequest) {
        Brand brand = brandRepository.findById(collabEventListRequest.getBrand_id())
                .get();

        CollabPlatform collabPlatform = collabPlatformRepository.findById(collabEventListRequest.getCollabPlatform_id())
                .get();

        List<CollabEvent> collabEvents = collabEventRepository.findByBrandAndCollabPlatform(brand, collabPlatform);

        if(collabEvents.size() == 0) {
            throw new DbNotFoundException();
        }

        return collabEvents;
    }
}
