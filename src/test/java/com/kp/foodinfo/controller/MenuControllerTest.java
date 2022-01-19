package com.kp.foodinfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandMenuKind;
import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.domain.Menu;
import com.kp.foodinfo.dto.FileTestUtilControllerDto;
import com.kp.foodinfo.repository.*;
import com.kp.foodinfo.request.MenuRequest;
import com.kp.foodinfo.service.FileService;
import com.kp.foodinfo.service.MenuService;
import com.kp.foodinfo.util.FileTestUtil;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.MenuListVo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    FoodRepository foodRepository;

    @Mock
    FileService fileService;

    @Autowired
    MenuSizeRepositroy menuSizeRepositroy;



    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Transactional
    public void MENU_PROCESS_TEST() throws Exception {

        FileTestUtilControllerDto fileRequest = FileTestUtil.getTestMultifileController();

        // MenuService - FileService Change Mock
        Mockito.when(fileService.s3UploadProcess(fileRequest.getFile())).thenReturn("test/test.jpg");
        MenuService menuService = new MenuService(menuRepository, brandMenuKindRepository, fileService, brandRepository, menuSizeRepositroy);
        MenuController menuController = new MenuController(menuService);
        mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();



        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "/test/test.jpg", new Date(), food);
        brandRepository.save(brand);

        BrandMenuKind brandMenuKind = new BrandMenuKind("Main", 1, brand);
        brandMenuKindRepository.save(brandMenuKind);

        // Setting Request
        MenuRequest menuRequest = new MenuRequest("normalPizza", 20000, brandMenuKind.getId());

        BasicVo basicVo = new BasicVo("success");

        String jsonBasicVo = objectMapper.writeValueAsString(basicVo);

        //when then
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/admin/menuprocess")
                .file(fileRequest.getFile())
                .requestAttr("request", fileRequest.getRequest())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menuRequest)))
                .andExpect(content().string(jsonBasicVo))
                .andDo(print());
    }

    @Test
    @Transactional
    public void MENU_LIST_TEST() throws Exception {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "/test/test.jpg", new Date(), food);
        brandRepository.save(brand);

        BrandMenuKind brandMenuKind = new BrandMenuKind("Main", 1, brand);
        brandMenuKindRepository.save(brandMenuKind);

        List<Menu> menus = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Menu menu = new Menu("normalMenu", 20000, "/test/test.jpg", brandMenuKind);

            menuRepository.save(menu);
            menus.add(menu);
        }

        String jsonMenuListVo = objectMapper.writeValueAsString(new MenuListVo(menus));

        //when then
        this.mockMvc.perform(post("/api/menulist/" + brandMenuKind.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonMenuListVo))
                .andDo(print());

    }
}