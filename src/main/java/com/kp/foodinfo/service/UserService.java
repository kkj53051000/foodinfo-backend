package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Role;
import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.exception.UserNotFoundException;
import com.kp.foodinfo.request.JoinRequest;
import com.kp.foodinfo.request.LoginRequest;
import com.kp.foodinfo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(JoinRequest joinRequest) {

        Date nowDate = new Date();

        //비밀번호 암호화
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secuPw = encoder.encode(joinRequest.getUserpw());

        User user = new User(joinRequest.getUserid(), secuPw, joinRequest.getEmail(), nowDate, Role.ADMIN);

        userRepository.save(user);
    }

    public User loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByUserid(loginRequest.getUserid())
                .orElseThrow(() -> new UserNotFoundException("abc123"));

        //비밀번호 복호화
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(encoder.matches(loginRequest.getUserpw(), user.getUserpw())) { //비밀번호가 맞을 경우, matches로 비밀번호 비교
            return user;
        }else{
            return null;
        }
    }
}
