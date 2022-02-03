package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Role;
import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.exception.UserExistsException;
import com.kp.foodinfo.exception.UserNotFoundException;
import com.kp.foodinfo.request.ChangeUserPwRequest;
import com.kp.foodinfo.request.JoinRequest;
import com.kp.foodinfo.request.LoginRequest;
import com.kp.foodinfo.repository.UserRepository;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.HeaderUserInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.Basic;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final JavaMailSender mailSender;

    public void saveUser(JoinRequest joinRequest) {
        log.info("saveUser() : in");

        //회원가입시 동일 이메일의 emailCheck false 계정이 있을때. ( 기존 계정 삭제 후 재 가입 )
        int userCheckCount = userRepository.countByEmail(joinRequest.getEmail());

        System.out.printf("userCheckCount : " + userCheckCount);

        if(userCheckCount != 0) {
            User user = userRepository.findByEmail(joinRequest.getEmail()).get();

            //Email 인증 안되어있을 때
            if (!user.isEmailCheck()) {
                userRepository.delete(user);
            }else { //Email 인증된 계정이 존재함
                throw new UserExistsException();
            }
        }

        //메일인증 부분
        //회원 인증 UUID 생성
        String uuid = UUID.randomUUID().toString();

        String emailUuid = joinRequest.getEmail() + uuid;

//        new Thread(() -> {
//            try {
//                Random random = new Random();
//                int auth = random.nextInt();
//
//                MimeMessage message = mailSender.createMimeMessage();
//                MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
//
//                messageHelper.setFrom("siktamstamsik@gmail.com"); // 보내는사람 생략하면 정상작동을 안함
//                messageHelper.setTo(joinRequest.getEmail()); // 받는사람 이메일
//                messageHelper.setSubject("식탐의탐식 회원가입 인증 메일입니다."); // 메일제목은 생략이 가능하다
//                messageHelper.setText("클릭 후 인증을 완료하세요 : " + "http://localhost:8080/api/emailauthprocess?uuid=" + emailUuid); // 메일 내용
//
//                mailSender.send(message);
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        }).start();

        Date nowDate = new Date();

        //비밀번호 암호화
        log.info("saveUser() : password encryption");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secuPw = encoder.encode(joinRequest.getUserpw());

        User user = new User(joinRequest.getEmail(), secuPw, nowDate, emailUuid, false, Role.ADMIN);

        log.info("saveUser() - UserRepository - save() : run");
        userRepository.save(user);
    }

    public void emailAuth(String uuid) {
        User user = userRepository.findByEmailUuid(uuid).orElseThrow(() -> new EmailAuthFailException());

        user.setEmailCheck(true);
    }

    class EmailAuthFailException extends RuntimeException {

    }

    public User loginUser(LoginRequest loginRequest) {
        log.info("loginUser() - UserRepository - findByUserid() : run");
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("abc123"));

        //비밀번호 복호화
        log.info("loginUser() : password decryption");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(encoder.matches(loginRequest.getUserpw(), user.getUserpw())) { //비밀번호가 맞을 경우, matches로 비밀번호 비교
            log.info("loginUser() : password match");
            log.info("loginUser() : User return");
            return user;
        }else{
            log.error("loginProcess() - UserNotFoundException() : password not match");
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
}
