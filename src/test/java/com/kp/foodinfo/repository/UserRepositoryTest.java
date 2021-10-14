package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Role;
import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.service.MemberService;
import com.kp.foodinfo.service.PaymentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Mock
    MemberService memberService;

    @Test
    @Transactional
    @Rollback(value = false)
    void USER_CREATE_TEST() {
        //given
        Date now = new Date();
        User user = new User("test", "test", "test@naver.com", now, Role.USER);

        //when
        userRepository.save(user);

        //then
        Assertions.assertNotNull(user.getId());
    }

    @Test
    @Transactional
    void USER_FIND_USERID_TEST() {
        //given
        Date now = new Date();
        User user = new User("test", "test", "test@naver.com", now, Role.USER);

        //when
        userRepository.save(user);

        //then
        Assertions.assertEquals(userRepository.findByUserid("test").get(), user);
    }


    // 다른 서비스에 요청을 보내는 TestCode의 경우 Mock객체를 만들어서 테스트하는 예제
    @Test
    void temp() {
        // given
        MemberService mockMemberService = new MemberService() {
            @Override
            public String getMemberName() {
                return "my name!!!";
            }
        };

        PaymentService paymentService = new PaymentService(mockMemberService);

        // when
        paymentService.payment();

        // then
        // my name!!! .... payment ... insert ...
    }

    @Test
    void temp2() {
        System.out.println(">>> " + memberService.getClass().getName());

        Mockito.when(memberService.getMemberName()).thenReturn("abcabc123123");
        System.out.println(">>> " + memberService.getMemberName());

//        // given
//        MemberService mockMemberService = new MemberService() {
//            @Override
//            public String getMemberName() {
//                return "my name!!!";
//            }
//        };
//
//        PaymentService paymentService = new PaymentService(mockMemberService);
//
//        // when
//        paymentService.payment();
//
//        // then
//        // my name!!! .... payment ... insert ...
    }


    public void abc() {
        // my-server insert1...

        // other server insert...

        // try
        // my-server insert2...
        // catch other server delete...

        // commit()
    }
}