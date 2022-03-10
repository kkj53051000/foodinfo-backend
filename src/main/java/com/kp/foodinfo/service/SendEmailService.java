package com.kp.foodinfo.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.kp.foodinfo.dto.EmailSenderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendEmailService {
    private final AmazonSimpleEmailService amazonSimpleEmailService;

    public void send(final String subject, final String content, final List<String> receivers) {
        final EmailSenderDto senderDto = EmailSenderDto.builder() // 1
                .to(receivers)
                .subject(subject)
                .content(content)
                .build();

        final SendEmailResult sendEmailResult = amazonSimpleEmailService // 2
                .sendEmail(senderDto.toSendRequestDto());

        sendingResultMustSuccess(sendEmailResult); // 3
    }


    private void sendingResultMustSuccess(final SendEmailResult sendEmailResult) {
        if (sendEmailResult.getSdkHttpMetadata().getHttpStatusCode() != 200) {
            log.error("{}", sendEmailResult.getSdkResponseMetadata().toString());
        }
    }


}
