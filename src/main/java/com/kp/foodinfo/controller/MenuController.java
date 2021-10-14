package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.Menu;
import com.kp.foodinfo.request.MenuRequest;
import com.kp.foodinfo.service.MenuService;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/menuprocess")
    public BasicVo menuUploadProcess(@RequestParam("file") MultipartFile file, @RequestParam MenuRequest menuRequest, HttpServletRequest request) {

        String realPath = request.getServletContext().getRealPath("");

        menuService.saveMenu(file, menuRequest, realPath);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    @PostMapping("/menulist")
    public List<Menu> menuList(@RequestParam long menuKind_id) {
        List<Menu> menus = menuService.getMenus(menuKind_id);

        return menus;
    }
}
