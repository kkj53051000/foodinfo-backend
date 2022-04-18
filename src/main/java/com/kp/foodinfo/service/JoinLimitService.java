package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.JoinLimit;
import com.kp.foodinfo.repository.JoinLimitRepository;
import com.kp.foodinfo.repository.UserRepository;
import com.kp.foodinfo.util.DateFormatUtil;
import com.kp.foodinfo.util.ReturnStatus;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class JoinLimitService {
    private final JoinLimitRepository joinLimitRepository;

    private final UserRepository userRepository;

    public BasicVo saveJoinLimit(int joinLimitNum) {
        JoinLimit joinLimit = new JoinLimit(joinLimitNum);

        joinLimitRepository.save(joinLimit);

        return new BasicVo(ReturnStatus.success);
    }

    public BasicVo joinCheck() {
        String today = DateFormatUtil.dateToStringProcess(new Date());

        int todayJoinCount = userRepository.countByJoinDateStr(today);

        if (joinLimitRepository.findAll().size() == 0) {
            JoinLimit joinLimit = new JoinLimit(90);
            joinLimitRepository.save(joinLimit);
        }

        int joinLimit = joinLimitRepository.findAll().get(0).getLimitNum();

        if (todayJoinCount > joinLimit) {
            return new BasicVo(ReturnStatus.failure);
        } else {
            return new BasicVo(ReturnStatus.success);
        }

    }
}
