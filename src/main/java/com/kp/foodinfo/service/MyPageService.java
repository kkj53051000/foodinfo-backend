package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.repository.FollowRepository;
import com.kp.foodinfo.repository.UserRepository;
import com.kp.foodinfo.vo.MyPageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {
    private final UserRepository userRepository;

    private final FollowRepository followRepository;

    public MyPageVo getMyPageInfo(Long userId) {

        User user = userRepository.findById(userId).get();

        String email = user.getEmail();

        int followCount = (int)followRepository.countByUser(user);

        System.out.println("followCount : "  + followCount);

        return new MyPageVo(email, followCount);
    }
}
