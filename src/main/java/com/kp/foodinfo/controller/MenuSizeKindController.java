package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.MenuSizeKindRequest;
import com.kp.foodinfo.service.MenuSizeKindService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.MenuSizeKindListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuSizeKindController {
    private final MenuSizeKindService menuSizeKindService;


    @PostMapping("/admin/menusizekindprocess")
    public BasicVo menuSizeKindProcess(@RequestBody MenuSizeKindRequest menuSizeKindRequest) {
        BasicVo basicVo = menuSizeKindService.saveMenuSizeKind(menuSizeKindRequest);

        return basicVo;
    }

    @GetMapping("/menusizekindlist")
    public MenuSizeKindListVo menuSizeKindList() {

        MenuSizeKindListVo menuSizeKindListVo = menuSizeKindService.getMenuSizeKindList();

        return menuSizeKindListVo;
    }


}
