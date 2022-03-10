package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.SiktamEvent;
import com.kp.foodinfo.repository.SiktamEventRepository;
import com.kp.foodinfo.request.SiktamEventRequest;
import com.kp.foodinfo.util.ReturnStatus;
import com.kp.foodinfo.util.DateFormatUtil;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.SiktamEventListVo;
import com.kp.foodinfo.vo.SiktamEventVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SiktamEventService {
    private final SiktamEventRepository siktamEventRepository;

    private final FileService fileService;

    public BasicVo saveSiktamEvent(MultipartFile file, SiktamEventRequest siktamEventRequest) throws IOException {

        String clientPath = fileService.s3UploadProcess(file);

        SiktamEvent siktamEvent = SiktamEvent.builder()
                .title(siktamEventRequest.getTitle())
                .img(clientPath)
                .startDate(DateFormatUtil.stringToDateDayProcess(siktamEventRequest.getStartDate()))
                .endDate(DateFormatUtil.stringToDateDayProcess(siktamEventRequest.getEndDate()))
                .startDateInt(DateFormatUtil.stringToIntegerProcess(siktamEventRequest.getStartDate()))
                .endDateInt(DateFormatUtil.stringToIntegerProcess(siktamEventRequest.getEndDate()))
                .winnerCount(siktamEventRequest.getWinnerCount())
                .status(true)
                .build();

        siktamEventRepository.save(siktamEvent);

        return new BasicVo(ReturnStatus.success);
    }

    public SiktamEventListVo getSiktamEventList() {
        List<SiktamEvent> siktamEvents = siktamEventRepository.findAll();

        Collections.reverse(siktamEvents);

        return new SiktamEventListVo(siktamEvents);
    }

    public SiktamEventVo getSiktamEvent(Long siktamevent_id) {
        SiktamEvent siktamEvent = siktamEventRepository.findById(siktamevent_id).get();

        return new SiktamEventVo(siktamEvent);
    }
}
