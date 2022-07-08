package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Menu;
import com.kp.foodinfo.domain.MenuReview;
import com.kp.foodinfo.repository.MenuRepository;
import com.kp.foodinfo.repository.MenuReviewRepository;
import com.kp.foodinfo.repository.MenuReviewRequest;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuReviewService {
    private final MenuReviewRepository menuReviewRepository;

    private final MenuRepository menuRepository;

    public BasicVo saveMenuReview(MenuReviewRequest menuReviewRequest) {

        Menu menu = menuRepository.findById(menuReviewRequest.getMenuId())
                .get();

        MenuReview menuReview = MenuReview.builder()
                .reviewContent(menuReviewRequest.getReviewContent())
                .rePurchase(menuReviewRequest.getRePurchase())
                .deleteAt(false)
                .menu(menu)
                .build();

        menuReviewRepository.save(menuReview);

        return new BasicVo("success");
    }
}
