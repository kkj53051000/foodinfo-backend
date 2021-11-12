package com.kp.foodinfo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandMenuKind;
import com.kp.foodinfo.domain.Menu;
import com.kp.foodinfo.dto.FileTestUtilControllerDto;
import com.kp.foodinfo.repository.BrandMenuKindRepository;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.MenuRepository;
import com.kp.foodinfo.service.MenuService;
import com.kp.foodinfo.util.FileTestUtil;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.MenuListVo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MenuControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    BrandMenuKindRepository brandMenuKindRepository;

    @Autowired
    MenuRepository menuRepository;

    @Test
    @Transactional
    public void MENU_PROCESS_TEST() throws Exception {
        // MenuService Change Mock
        MenuService menuService = Mockito.mock(MenuService.class);
        MenuController menuController = new MenuController(menuService);
        mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();

        FileTestUtilControllerDto fileRequest = FileTestUtil.getTestMultifileController();

        //given
        Brand brand = new Brand("pizzaHut", "/test/test.jpg");
        brandRepository.save(brand);

        BrandMenuKind brandMenuKind = new BrandMenuKind("Main", 1, brand);
        brandMenuKindRepository.save(brandMenuKind);

        MultiValueMap<String, String> menuRequest = new LinkedMultiValueMap<>();

        menuRequest.add("name", "normalPizza");
        menuRequest.add("price", "20000");
        menuRequest.add("brandMenuKind_id", Long.toString(brandMenuKind.getId()));

        ObjectMapper objectMapper = new ObjectMapper();

        BasicVo basicVo = new BasicVo("success");

        String jsonBasicVo = objectMapper.writeValueAsString(basicVo);

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/admin/menuprocess")
                .file(fileRequest.getFile())
                .requestAttr("request", fileRequest.getRequest())
                .params(menuRequest))
                .andExpect(content().string(jsonBasicVo))
                .andDo(print());
    }

    @Test
    @Transactional
    public void MENU_LIST_TEST() throws Exception {
        //given
        Brand brand = new Brand("pizzaHut", "/test/test.jpg");
        brandRepository.save(brand);

        BrandMenuKind brandMenuKind = new BrandMenuKind("Main", 1, brand);
        brandMenuKindRepository.save(brandMenuKind);

        List<Menu> menus = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Menu menu = new Menu("normalMenu", 20000, "/test/test.jpg", brandMenuKind);

            menuRepository.save(menu);

            menus.add(menu);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonMenuListVo = objectMapper.writeValueAsString(new MenuListVo(menus));

        this.mockMvc.perform(post("/api/menulist").param("brandMenuKind_id", Long.toString(brandMenuKind.getId())))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonMenuListVo))
                .andDo(print());

    }
}