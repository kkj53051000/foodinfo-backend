package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandMenuKind;
import com.kp.foodinfo.domain.Menu;
import com.kp.foodinfo.domain.MenuSize;
import com.kp.foodinfo.exception.DbNotFoundException;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.MenuSizeRepositroy;
import com.kp.foodinfo.request.MenuRequest;
import com.kp.foodinfo.repository.BrandMenuKindRepository;
import com.kp.foodinfo.repository.MenuRepository;
import com.kp.foodinfo.util.FormatUtil;
import com.kp.foodinfo.vo.MenuListVo;
import com.kp.foodinfo.vo.MenuSizeVo;
import com.kp.foodinfo.vo.MenuVo;
import com.kp.foodinfo.vo.MenuVoList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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

    public void saveMenu(MultipartFile file, MenuRequest menuRequest) throws IOException {
        log.info("saveMenu() : in");
        log.info("saveMenu() - FileService - s3UploadProcess() : run");
        String clientPath = fileService.s3UploadProcess(file);

        System.out.println("clientPath : " + clientPath);
        System.out.println("name : " + menuRequest.getName());
        System.out.println("brandMenuKind_id : " + menuRequest.getBrandMenuKind_id());

        log.info("saveMenu() - BrandMenuKindRepository - findById() : run");
        BrandMenuKind brandMenuKind = brandMenuKindRepository.findById(menuRequest.getBrandMenuKind_id()).get();

        Menu menu = new Menu(menuRequest.getName(), menuRequest.getPrice(), clientPath, brandMenuKind);

        log.info("saveMenu() - MenuRepository - save() : run");
        menuRepository.save(menu);
    }

    public MenuVoList getMenuList(long menuKind_id){
        log.info("getMenuList() : in");
        log.info("getMenuList() - BrandMenuKindRepository - findById() : run");
        BrandMenuKind brandMenuKind = brandMenuKindRepository.findById(menuKind_id)
                .get();

        System.out.println("brandMenuKind id : " + brandMenuKind.getId());

        log.info("getMenuList() - MenuRepository - findByBrandMenuKind() : run");
        List<Menu> menus = menuRepository.findByBrandMenuKind(brandMenuKind);


        List<MenuVo> menuVos = new ArrayList<>();

        for (int i = 0; i < menus.size(); i++) {
            long count = menuSizeRepositroy.countByMenu(menus.get(i));

            System.out.println("count : " + count);

            if(count > 0){
                List<MenuSize> menuSizes = menuSizeRepositroy.findByMenu(menus.get(i));

                List<MenuSizeVo> menuSizeVos = new ArrayList<>();

                for(int j = 0; j < menuSizes.size(); j++) {
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
            }else{
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

        System.out.println("menus.size() : " + menus.size());
        System.out.println("menuVos.size() : " + menuVos.size());

        if(menus.size() == 0){
            log.error("getMenuList() - DbNotFoundException() : menu not found");
            throw new DbNotFoundException();
        }

        log.info("getMenuList() : MenuListVo return");
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
}
