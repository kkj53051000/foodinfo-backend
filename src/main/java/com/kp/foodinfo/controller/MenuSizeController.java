package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.MenuSizeRequest;
import com.kp.foodinfo.service.MenuSizeService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.MenuSizeDeleteListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/admin/menusizedelete/{id}")
    public BasicVo menuSizeDelete(@PathVariable("id") long menuSizeId) {
        return menuSizeService.deleteMenuSize(menuSizeId);
    }

    @GetMapping("/menusizelistfindbrandid/{id}")
    public MenuSizeDeleteListVo menuSizeList(@PathVariable("id") long brandId) {
        return menuSizeService.menuSizeListFindBrandId(brandId);
    }
}
