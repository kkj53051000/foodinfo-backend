package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandMenuKind;
import com.kp.foodinfo.domain.Menu;
import com.kp.foodinfo.dto.FileTestUtilDto;
import com.kp.foodinfo.repository.BrandMenuKindRepository;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.MenuRepository;
import com.kp.foodinfo.util.FileTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MenuServiceTest {
    @Autowired
    MenuService menuService;

    @Mock
    FileService fileService;

    @Autowired
    BrandMenuKindRepository brandMenuKindRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    BrandRepository brandRepository;

    @Test
    @Transactional
    void MENU_SAVE_TEST() {
        //given
        //Mock MenuService 생성
        FileTestUtilDto fileTestUtilDto = FileTestUtil.getTestMultifile();
        Mockito.when(fileService.imageUploadProcess(fileTestUtilDto.getMultipartFile(), fileTestUtilDto.getRealPath())).thenReturn("test/test.jpg");
        MenuService menuService = new MenuService(menuRepository, brandMenuKindRepository, fileService);

        //Brand 저장
        Brand brand = new Brand("pizzaHut", "test/test.jpg");
        brandRepository.save(brand);

        //BrandMenuKind 저장
        BrandMenuKind brandMenuKind = new BrandMenuKind("메인 메뉴", 1, brand);
        brandMenuKindRepository.save(brandMenuKind);

        Menu menu = new Menu("normalPizza", 20000, "test/test.jpg", brandMenuKind);

        //when
        menuRepository.save(menu);

        //then
        Assertions.assertNotNull(menuRepository.findById(menu.getId()).get());
        Assertions.assertEquals(menuRepository.findById(menu.getId()).get().getName(), menu.getName());
    }

    @Test
    @Transactional
    void MENU_GET_LIST_TEST() {
        //given
        //Mock MenuService 생성
        FileTestUtilDto fileTestUtilDto = FileTestUtil.getTestMultifile();
        Mockito.when(fileService.imageUploadProcess(fileTestUtilDto.getMultipartFile(), fileTestUtilDto.getRealPath())).thenReturn("test/test.jpg");
        MenuService menuService = new MenuService(menuRepository, brandMenuKindRepository, fileService);

        //Brand 저장
        Brand brand = new Brand("pizzaHut", "test/test.jpg");
        brandRepository.save(brand);

        //BrandMenuKind 저장
        BrandMenuKind brandMenuKind = new BrandMenuKind("메인 메뉴", 1, brand);
        brandMenuKindRepository.save(brandMenuKind);

        for (int i = 0; i < 10; i++) {
            Menu menu = new Menu("normalPizza" + i, 20000, "test/test.jpg", brandMenuKind);

            menuRepository.save(menu);
        }

        //when
        List<Menu> menus = menuService.getMenuList(brandMenuKind.getId());

        //then
        Assertions.assertEquals(menus.size(), 10);
    }
}