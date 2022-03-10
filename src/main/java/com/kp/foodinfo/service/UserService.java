package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Role;
import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.exception.UserEmailCheckFailExceotion;
import com.kp.foodinfo.exception.UserExistsException;
import com.kp.foodinfo.exception.UserNotFoundException;
import com.kp.foodinfo.request.ChangeUserPwRequest;
import com.kp.foodinfo.request.JoinRequest;
import com.kp.foodinfo.request.LoginRequest;
import com.kp.foodinfo.repository.UserRepository;
import com.kp.foodinfo.util.DateFormatUtil;
import com.kp.foodinfo.util.ReturnStatus;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.HeaderUserInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final JavaMailSender mailSender;

    private final MailService mailService;

    private final SendEmailService sendEmailService;


    public void saveUser(JoinRequest joinRequest) {

        //회원가입시 동일 이메일의 emailCheck false 계정이 있을때. ( 기존 계정 삭제 후 재 가입 )
        int userCheckCount = userRepository.countByEmail(joinRequest.getEmail());

        if(userCheckCount != 0) {
            User user = userRepository.findByEmail(joinRequest.getEmail()).get();

            //Email 인증 안되어있을 때
            if (!user.isEmailCheck()) {
                userRepository.delete(user);
            }else { //Email 인증된 계정이 존재함
                throw new UserExistsException();
            }
        }


        // 회원 인증 UUID 생성
        String uuid = UUID.randomUUID().toString();
        String emailUuid = joinRequest.getEmail() + uuid;

        // 메일 인증

        mailService.mailSend(joinRequest, emailUuid);
//        MailSendUtil.mainSender(joinRequest, emailUuid);


//        List<String> to = new ArrayList<>();
//        to.add(joinRequest.getEmail());
//
//        sendEmailService.send("ttest", "ttest", to);

        //비밀번호 암호화
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secuPw = encoder.encode(joinRequest.getUserpw());

        User user = new User(joinRequest.getEmail(), secuPw, new Date(), DateFormatUtil.dateToStringProcess(new Date()), new Date(), emailUuid, false, Role.USER, false);

        userRepository.save(user);
    }

    public void emailAuth(String uuid) {
        User user = userRepository.findByEmailUuid(uuid).orElseThrow(() -> new EmailAuthFailException());

        user.setEmailCheck(true);
    }

    class EmailAuthFailException extends RuntimeException {

    }

    public User loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("abc123"));

        if(!user.isEmailCheck()){
            throw new UserEmailCheckFailExceotion();
        }

        user.setRecentlyVisitDate(new Date());

        //비밀번호 복호화
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(encoder.matches(loginRequest.getUserpw(), user.getUserpw())) { //비밀번호가 맞을 경우, matches로 비밀번호 비교

            return user;
        }else{
            throw new UserNotFoundException();
        }
    }

    public HeaderUserInfoVo getHeaderUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new DbNotFoundException());

        HeaderUserInfoVo headerUserInfoVo = new HeaderUserInfoVo(user.getEmail());

        return headerUserInfoVo;
    }

    public BasicVo updateUserPw(Long userId, ChangeUserPwRequest changeUserPwRequest){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = userRepository.findById(userId).get();

        if(encoder.matches(changeUserPwRequest.getNowUserPw(), user.getUserpw())) {
            // 바뀐 비밀번호 암호화
            user.setUserpw(encoder.encode(changeUserPwRequest.getChangeUserPw()));

            return new BasicVo("success");
        }else {
            return new BasicVo("failure", "nowUserPwNotMatched");
        }
    }

    public BasicVo deleteUser(Long userId, Long requestUserId) {
        if(userId == requestUserId){
            User user = userRepository.findById(userId).get();

            String uuid = UUID.randomUUID().toString();

            user.setDeleteAt(true);
            user.setEmail("delete" + uuid);
            user.setUserpw(uuid);
        }else {
            throw new UserNotFoundException();
        }

        return new BasicVo(ReturnStatus.success);
    }
}
