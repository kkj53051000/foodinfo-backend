package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandEvent;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.request.BrandEventRequest;
import com.kp.foodinfo.repository.BrandEventRepository;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.util.StringToDateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandEventService {
    private final BrandEventRepository brandEventRepository;

    private final BrandRepository brandRepository;

    private final FileService fileService;

    public void saveBrandEvent(MultipartFile file, BrandEventRequest brandEventRequest, String realPath) {

        String clientPath = fileService.imageUploadProcess(file, realPath);

        Brand brand = brandRepository.findById(brandEventRequest.getBrand_id())
                .get();

        // Date String -> Date
        Date startDate = StringToDateUtil.stringToDateProcess(brandEventRequest.getStartDate() + " " + brandEventRequest.getStartTime());
        Date endDate = StringToDateUtil.stringToDateProcess(brandEventRequest.getEndDate() + " " + brandEventRequest.getEndTime());

        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        BrandEvent brandEvent = new BrandEvent(brandEventRequest.getTitle(), brandEventRequest.getContent(), clientPath, startDate, endDate, brand);

        brandEventRepository.save(brandEvent);
    }

    public List<BrandEvent> getBrandEvents(long brand_id) {

        Brand brand = brandRepository.findById(brand_id)
                .get();

        List<BrandEvent> brandEvents = brandEventRepository.findByBrand(brand);

        if(brandEvents.size() == 0) {
            throw new DbNotFoundException();
        }

        return brandEvents;
    }
}
