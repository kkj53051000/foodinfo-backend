package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.dto.BrandDto;
import com.kp.foodinfo.dto.FileTestUtilDto;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.util.FileTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BrandServiceTest {
    @Autowired
    BrandService brandService;

    @Autowired
    BrandRepository brandRepository;

    @Test
    @Transactional
    void BRAND_SAVE_TEST() throws IOException {
        //given
        //파일 가져오기
        FileTestUtilDto fileTestUtilDto = FileTestUtil.getTestMultifile();

        FileService fileService = Mockito.mock(FileService.class);
        BrandService brandService = new BrandService(brandRepository, fileService);

        //brandDto 파라미터 생성
        BrandDto brandDto = new BrandDto("pizzaHut", fileTestUtilDto.getMultipartFile());

        //when
        brandService.saveBrand(brandDto);

        //then
        Assertions.assertNotNull(brandRepository.findByName(brandDto.getName()));
    }

    @Test
    @Transactional
    void BRAND_GET_LIST_TEST() {
        //given
        List<Brand> brands1 = new ArrayList<>();

        for(int i = 0; i < 5; i ++) {
            Brand brand = new Brand("pizzaHut" + i, "test/test.jpg");

            brands1.add(brand);

            brandRepository.save(brand);
        }

        //when
        List<Brand> brands2 = brandService.getBrandList();

        //then
        Assertions.assertEquals(brands1, brands2);
    }
}