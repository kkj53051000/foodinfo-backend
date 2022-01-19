package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.Menu;
import com.kp.foodinfo.request.MenuRequest;
import com.kp.foodinfo.service.MenuService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.MenuListVo;
import com.kp.foodinfo.vo.MenuVoList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/admin/menuprocess")
    public BasicVo menuProcess(@RequestPart(name = "file", required = true) MultipartFile file, @RequestPart(name = "value", required = false) MenuRequest menuRequest) throws IOException {
        log.info("menuProcess() : in");
        log.info("menuProcess() - MenuService - saveMenu() : run");
        menuService.saveMenu(file, menuRequest);

        BasicVo basicVo = new BasicVo("success");

        log.info("menuProcess() : BasicVo return");
        return basicVo;
    }

    @GetMapping("/menulist/{id}")
    public MenuVoList menuList(@PathVariable("id") long brandMenuKind_id) {
        log.info("menuList() : in");
        log.info("menuList() - MenuService - getMenuList() : run");
        MenuVoList menuVoList = menuService.getMenuList(brandMenuKind_id);

        log.info("menuList() : MenuListVo return");
        return menuVoList;
    }

    @GetMapping("/menualllist/{id}")
    public MenuListVo menuAllList(@PathVariable("id") long brand_id) {
        MenuListVo menuListVo = menuService.getMenuAll(brand_id);

        return menuListVo;
    }
}
