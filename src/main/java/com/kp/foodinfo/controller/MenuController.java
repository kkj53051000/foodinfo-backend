package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.Menu;
import com.kp.foodinfo.request.MenuRequest;
import com.kp.foodinfo.service.MenuService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.MenuListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/admin/menuprocess")
    public BasicVo menuProcess(MultipartFile file, MenuRequest menuRequest, HttpServletRequest request) throws IOException {

        String realPath = request.getServletContext().getRealPath("");

        menuService.saveMenu(file, menuRequest);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    @PostMapping("/menulist")
    public MenuListVo menuList(long brandMenuKind_id) {
        List<Menu> menus = menuService.getMenuList(brandMenuKind_id);

        return new MenuListVo(menus);
    }
}
