package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.SiktamEvent;
import com.kp.foodinfo.domain.SiktamEventEntry;
import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.exception.EventEntryLateException;
import com.kp.foodinfo.exception.SiktamEventEntryOverlapException;
import com.kp.foodinfo.repository.SiktamEventEntryRepository;
import com.kp.foodinfo.repository.SiktamEventRepository;
import com.kp.foodinfo.repository.UserRepository;
import com.kp.foodinfo.request.SiktamEventEntryRequest;
import com.kp.foodinfo.util.ReturnStatus;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class SiktamEventEntryService {
    private final SiktamEventEntryRepository siktamEventEntryRepository;

    private final UserRepository userRepository;

    private final SiktamEventRepository siktamEventRepository;

    public BasicVo saveSiktamEventEntry(Long userId, SiktamEventEntryRequest siktamEventEntryRequest) {

        User user = userRepository.findById(userId).get();

        SiktamEvent siktamEvent = siktamEventRepository.findById(siktamEventEntryRequest.getSiktamEventId()).get();

        int siktamEventEntryCheckCount = siktamEventEntryRepository.countByUserAndSiktamEvent(user, siktamEvent);


        Date now = new Date();

        if(siktamEvent.getEndDate().after(now)){
            throw new EventEntryLateException();
        }

        if(siktamEventEntryCheckCount != 0) { // 중복참여
            // 예외처리
            throw new SiktamEventEntryOverlapException();
        }

        SiktamEventEntry siktamEventEntry = new SiktamEventEntry(siktamEventEntryRequest.getPhoneNumber(), user, siktamEvent);

        siktamEventEntryRepository.save(siktamEventEntry);

        return new BasicVo(ReturnStatus.success);
    }
}
