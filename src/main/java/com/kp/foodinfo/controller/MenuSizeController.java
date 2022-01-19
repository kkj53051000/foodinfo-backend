package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.MenuSizeRequest;
import com.kp.foodinfo.service.MenuSizeService;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuSizeController {
    private final MenuSizeService menuSizeService;

    @PostMapping("/admin/menusizeprocess")
    public BasicVo menuSizeProcess(@RequestBody MenuSizeRequest menuSizeRequest) {

        BasicVo basicVo = menuSizeService.saveMenuSize(menuSizeRequest);

        return basicVo;
    }
}
