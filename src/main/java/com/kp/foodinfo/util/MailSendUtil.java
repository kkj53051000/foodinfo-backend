package com.kp.foodinfo.util;


import com.kp.foodinfo.request.JoinRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Random;
import java.util.UUID;

public class MailSendUtil {

    @Autowired
    private static JavaMailSender mailSender;

    @Profile("prod")
    public static void mainSender(JoinRequest joinRequest, String emailUuid) {
        try {
            Random random = new Random();
            int auth = random.nextInt();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            messageHelper.setFrom("siktamstamsik@gmail.com"); // 보내는사람
            messageHelper.setTo(joinRequest.getEmail()); // 받는사람 이메일
            messageHelper.setSubject("식탐의탐식 회원가입 인증 메일입니다."); // 메일제목은 생략이 가능하다
            messageHelper.setText("클릭 후 인증을 완료하세요 : " + "https://api.siktamsik.com/api/emailauthprocess?uuid=" + emailUuid); // 메일 내용

            mailSender.send(message);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
