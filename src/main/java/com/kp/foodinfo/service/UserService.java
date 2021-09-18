package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Role;
import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.form.JoinForm;
import com.kp.foodinfo.form.LoginForm;
import com.kp.foodinfo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void saveUser(JoinForm joinForm) {

        Date nowDate = new Date();

        //비밀번호 암호화
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secuPw = encoder.encode(joinForm.getUserpw());

        User user = new User(joinForm.getUserid(), secuPw, joinForm.getEmail(), nowDate, Role.ADMIN);

        userRepository.save(user);
    }

    public User loginUser(LoginForm loginForm) {
        User user = userRepository.findByUserid(loginForm.getUserid());

        //비밀번호 복호화
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(user == null){
            return null;
        }else if(encoder.matches(loginForm.getUserpw(), user.getUserpw())){ //비밀번호가 맞을 경우, matches로 비밀번호 비교
            return user;
        }else{ //비밀번호가 틀릴 경우
            return user;
        }
    }
}
