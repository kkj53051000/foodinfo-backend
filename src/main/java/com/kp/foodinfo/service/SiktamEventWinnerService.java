package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.SiktamEvent;
import com.kp.foodinfo.domain.SiktamEventEntry;
import com.kp.foodinfo.domain.SiktamEventWinner;
import com.kp.foodinfo.repository.SiktamEventEntryRepository;
import com.kp.foodinfo.repository.SiktamEventRepository;
import com.kp.foodinfo.repository.SiktamEventWinnerRepository;
import com.kp.foodinfo.request.SiktamEventWinnerRequest;
import com.kp.foodinfo.util.ReturnStatus;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.SiktamEventWinnerListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class SiktamEventWinnerService {
    private final SiktamEventWinnerRepository siktamEventWinnerRepository;

    private final SiktamEventRepository siktamEventRepository;

    private final SiktamEventEntryRepository siktamEventEntryRepository;


    public BasicVo setSiktamEventWinner(long siktamEvent_id) {
        SiktamEvent siktamEvent = siktamEventRepository.findById(siktamEvent_id).get();

        if (!siktamEvent.isStatus()) {
            // 이미 당첨 처리됨 Exception
        }

        List<SiktamEventEntry> siktamEventEntries = siktamEventEntryRepository.findBySiktamEvent(siktamEvent);

        // 랜덤 추출 (n개)
        List<SiktamEventEntry> siktamEventWinners = new ArrayList<>();

        for (int i = 0; i < siktamEvent.getWinnerCount(); i++) {
            double randomValue = Math.random();
            int random = (int) (randomValue * siktamEventEntries.size());


//            Random random = new Random();
//            int randomInt = random.nextInt(siktamEventEntries.size())+1;

            SiktamEventEntry randomSiktamEventEntry = siktamEventEntries.get(random);
            siktamEventWinners.add(randomSiktamEventEntry);

            // SiktamEventWinner 저장
            SiktamEventWinner siktamEventWinner = new SiktamEventWinner(randomSiktamEventEntry.getPhoneNumber(), randomSiktamEventEntry.getUser(), randomSiktamEventEntry.getSiktamEvent());
            siktamEventWinnerRepository.save(siktamEventWinner);

            siktamEventEntries.remove(random);
        }

        siktamEvent.setStatus(false);

        return new BasicVo(ReturnStatus.success);
    }


    public SiktamEventWinnerListVo getSiktamEventWinnerList(long siktamEvent_id) {
        SiktamEvent siktamEvent = siktamEventRepository.findById(siktamEvent_id).get();

        List<SiktamEventWinner> siktamEventWinners = siktamEventWinnerRepository.findBySiktamEvent(siktamEvent);

        return new SiktamEventWinnerListVo(siktamEventWinners);
    }
}
