package com.kp.foodinfo.controller;

import com.kp.foodinfo.repository.MenuReviewModifyRequest;
import com.kp.foodinfo.repository.MenuReviewRemoveRequest;
import com.kp.foodinfo.repository.MenuReviewRequest;
import com.kp.foodinfo.service.MenuReviewService;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuReviewController {
    private final MenuReviewService menuReviewService;

    @PostMapping("/user/menureviewprocess")
    public BasicVo menuReviewProcess(@RequestBody MenuReviewRequest menuReviewRequest) {
        return menuReviewService.saveMenuReview(menuReviewRequest);
    }

    @PostMapping("/user/menureviewmodify")
    public BasicVo menuReviewModify(@RequestBody MenuReviewModifyRequest menuReviewModifyRequest) {
        return menuReviewService.alertMenuReview(menuReviewModifyRequest);
    }

    @PostMapping("/user/menureviewremove")
    public BasicVo menuReviewRemove(@RequestBody MenuReviewRemoveRequest menuReviewRemoveRequest) {
        return menuReviewService.deleteMenuReview(menuReviewRemoveRequest);
    }
}
