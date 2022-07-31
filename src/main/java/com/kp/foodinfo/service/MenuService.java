package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.*;
import com.kp.foodinfo.dto.ExcelMenuDto;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.repository.*;
import com.kp.foodinfo.request.MenuRequest;
import com.kp.foodinfo.util.FormatUtil;
import com.kp.foodinfo.util.ReturnStatus;
import com.kp.foodinfo.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MenuService {
    private final MenuRepository menuRepository;

    private final BrandMenuKindRepository brandMenuKindRepository;

    private final FileService fileService;

    private final BrandRepository brandRepository;

    private final MenuSizeRepositroy menuSizeRepositroy;

    private final MenuSizeKindRepository menuSizeKindRepository;

    public void saveMenu(MultipartFile file, MenuRequest menuRequest) throws IOException {
        String clientPath = fileService.s3UploadProcess(file);

        BrandMenuKind brandMenuKind = brandMenuKindRepository.findById(menuRequest.getBrandMenuKind_id()).get();

        Menu menu = new Menu(menuRequest.getName(), menuRequest.getPrice(), clientPath, brandMenuKind);

        menuRepository.save(menu);
    }


    // 메뉴 엑셀 저장
    public BasicVo saveExcelMenu(MultipartFile file, long brand_id) throws IOException {


        Brand brand = brandRepository.findById(brand_id).get();

        List<ExcelMenuDto> excelMenuDtos = new ArrayList<>();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 3

        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            System.out.printf("에러");
        }

        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4

            Row row = worksheet.getRow(i);

//            System.out.printf("\n num : " + row.getCell(0).getNumericCellValue());
//            System.out.printf(" name : " + row.getCell(1).getStringCellValue());
//            System.out.printf(" type : " + row.getCell(2).getStringCellValue());
//            System.out.printf(" size : " + row.getCell(3).getStringCellValue());
//            System.out.printf(" price : " + row.getCell(4).getNumericCellValue());
//            System.out.printf(" img : " + row.getCell(5).getStringCellValue());

            // excel Data
            String name = row.getCell(1).getStringCellValue();
            name = name.replaceAll("&#37;", "%");
            String BrandMenuKindName = row.getCell(2).getStringCellValue();
            int price = (int) row.getCell(4).getNumericCellValue();
            String img = row.getCell(5).getStringCellValue();
            String size = row.getCell(3).getStringCellValue();


            // BrandMenuKind
            BrandMenuKind brandMenuKind = null;

            // 해당 데이터의 브랜드 메뉴 종류 유무 판단하기위한 count
            int brandMenuKindTargetCount = brandMenuKindRepository.countByNameAndBrand(BrandMenuKindName, brand);

            if (brandMenuKindTargetCount == 0) { // 메뉴 종류가 없음 ( 저장 )
                int brandBrandMenuKindCount = brandMenuKindRepository.countByBrand(brand);

                brandMenuKind = new BrandMenuKind(BrandMenuKindName, (brandBrandMenuKindCount + 1), brand);

                brandMenuKindRepository.save(brandMenuKind);
            } else { // 브랜드 메뉴 종류가 없음 ( select )
                brandMenuKind = brandMenuKindRepository.findByNameAndBrand(BrandMenuKindName, brand).get();
            }


            Menu menu = null;
            // 메뉴가 없을 때
//            if(menuRepository.countByNameAndBrandMenuKind(name, brandMenuKind) == 0) {
//
//            }

            // Size ( 사이즈가 있으면 MenuSize insert )
            if (!size.equals("null")) { // 사이즈 있는 메뉴 저장

                // 메뉴가 없을 때
                if (menuRepository.countByNameAndBrandMenuKind(name, brandMenuKind) == 0) {
                    menu = Menu.builder()
                            .name(name)
                            .price(price)
                            .img(img)
                            .brandMenuKind(brandMenuKind)
                            .build();


                    menuRepository.save(menu);
                } else { // 메뉴가 있으면 select
                    menu = menuRepository.findByNameAndBrandMenuKind(name, brandMenuKind).get();
                }


                MenuSizeKind menuSizeKind = null;

                if (menuSizeKindRepository.countBySize(size) == 0) {  // 메뉴 사이즈 종류가 없으면 저장
                    menuSizeKind = new MenuSizeKind(size);
                    menuSizeKindRepository.save(menuSizeKind);
                } else {  // 메뉴 사이즈 종류가 있다면 insert
                    menuSizeKind = menuSizeKindRepository.findBySize(size).get();
                }

                MenuSize menuSize = new MenuSize(menu, menuSizeKind, price);
                menuSizeRepositroy.save(menuSize);

            } else { // 일반 메뉴 저장
                menu = Menu.builder()
                        .name(name)
                        .price(price)
                        .img(img)
                        .brandMenuKind(brandMenuKind)
                        .build();


                menuRepository.save(menu);
            }
        }

        return new BasicVo(ReturnStatus.success);
    }

    public MenuVoList getMenuList(long menuKind_id) {
        BrandMenuKind brandMenuKind = brandMenuKindRepository.findById(menuKind_id)
                .get();

        List<Menu> menus = menuRepository.findByBrandMenuKind(brandMenuKind);


        List<MenuVo> menuVos = new ArrayList<>();

        for (int i = 0; i < menus.size(); i++) {
            long count = menuSizeRepositroy.countByMenu(menus.get(i));

            if (count > 0) {
                List<MenuSize> menuSizes = menuSizeRepositroy.findByMenu(menus.get(i));

                List<MenuSizeVo> menuSizeVos = new ArrayList<>();

                for (int j = 0; j < menuSizes.size(); j++) {
                    MenuSizeVo menuSizeVo = new MenuSizeVo(menuSizes.get(j).getMenuSizeKind().getSize(), menuSizes.get(j).getPrice());

                    menuSizeVos.add(menuSizeVo);
                }

                MenuVo menuVo = MenuVo.builder()
                        .id(menus.get(i).getId())
                        .name(menus.get(i).getName())
                        .price(null)
                        .menuSizeVo(menuSizeVos)
                        .img(menus.get(i).getImg())
                        .build();

                menuVos.add(menuVo);
            } else {
                MenuVo menuVo = MenuVo.builder()
                        .id(menus.get(i).getId())
                        .name(menus.get(i).getName())
                        .price(FormatUtil.menuPriceFormat(menus.get(i).getPrice()))
                        .menuSizeVo(null)
                        .img(menus.get(i).getImg())
                        .build();

                menuVos.add(menuVo);
            }
        }

        if (menus.size() == 0) {
            throw new DbNotFoundException();
        }

        return new MenuVoList(menuVos);
    }

    public MenuListVo getMenuAll(long brand_id) {
        Brand brand = brandRepository.findById(brand_id).get();

        List<BrandMenuKind> brandMenuKinds = brandMenuKindRepository.findByBrand(brand);

        List<Menu> menus = new ArrayList<>();

        for (int i = 0; i < brandMenuKinds.size(); i++) {
            List<Menu> tempMenus = menuRepository.findByBrandMenuKind(brandMenuKinds.get(i));

            menus.addAll(tempMenus);
        }

        return new MenuListVo(menus);
    }

    public BasicVo deleteMenu(long menu_id) {
        Menu menu = menuRepository.findById(menu_id).get();

        menuRepository.delete(menu);

        return new BasicVo(ReturnStatus.success);
    }

    public FrMenuRecentlyListVo frRecentMenuSelect() {
        List<Menu> menuList = menuRepository.findTop5ByOrderByIdDesc();

        return new FrMenuRecentlyListVo(menuList);
    }
}
