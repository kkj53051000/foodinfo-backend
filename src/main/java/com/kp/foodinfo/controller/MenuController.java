package com.kp.foodinfo.controller;

import com.kp.foodinfo.aop.LogExcutionTime;
import com.kp.foodinfo.domain.Item;
import com.kp.foodinfo.domain.Menu;
import com.kp.foodinfo.request.MenuRequest;
import com.kp.foodinfo.service.MenuService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.FrMenuRecentlyListVo;
import com.kp.foodinfo.vo.MenuListVo;
import com.kp.foodinfo.vo.MenuVoList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MenuController {
    private final MenuService menuService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/admin/menuprocess")
    public BasicVo menuProcess(@RequestPart(name = "file", required = true) MultipartFile file, @RequestPart(name = "value", required = false) MenuRequest menuRequest) throws IOException {
        menuService.saveMenu(file, menuRequest);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    @PostMapping("/admin/menuexcelprocess/{id}")
    public BasicVo menuExcelProcess(@RequestPart(value = "file", required = true) MultipartFile file, @PathVariable("id") long brand_id) throws IOException {
        return menuService.saveExcelMenu(file, brand_id);
    }

    @GetMapping("/menulist/{id}")
    public MenuVoList menuList(@PathVariable("id") long brandMenuKind_id) {

        ValueOperations<String, MenuVoList> valueOperations = redisTemplate.opsForValue();

        String redisName = "MenuController.menuList()" + String.valueOf(brandMenuKind_id);

        MenuVoList menuVoList = valueOperations.get(redisName);

        if (menuVoList == null) {
            menuVoList = menuService.getMenuList(brandMenuKind_id);
            valueOperations.set(redisName, menuVoList, 5L, TimeUnit.MINUTES);
        }

        return menuVoList;
    }

    @GetMapping("/menualllist/{id}")
    public MenuListVo menuAllList(@PathVariable("id") long brand_id) {
        MenuListVo menuListVo = menuService.getMenuAll(brand_id);

        return menuListVo;
    }

    @PostMapping("/admin/menudelete/{id}")
    public BasicVo menuDelete(@PathVariable("id") long menu_id) {
        return menuService.deleteMenu(menu_id);
    }

    @GetMapping("/frrecentmenu")
    public FrMenuRecentlyListVo frRecentMenu() {

        return menuService.frRecentMenuSelect();
    }
}
