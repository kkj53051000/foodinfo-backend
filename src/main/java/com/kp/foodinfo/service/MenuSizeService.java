package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.*;
import com.kp.foodinfo.repository.*;
import com.kp.foodinfo.request.MenuSizeRequest;
import com.kp.foodinfo.util.FormatUtil;
import com.kp.foodinfo.util.ReturnStatus;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.MenuListVo;
import com.kp.foodinfo.vo.MenuSizeDeleteListVo;
import com.kp.foodinfo.vo.MenuSizeDeleteVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuSizeService {
    private final MenuSizeRepositroy menuSizeRepositroy;

    private final MenuRepository menuRepository;

    private final MenuSizeKindRepository menuSizeKindRepository;

    private final BrandRepository brandRepository;

    private final BrandMenuKindRepository brandMenuKindRepository;

    public BasicVo saveMenuSize(MenuSizeRequest menuSizeRequest) {

        Menu menu = menuRepository.findById(menuSizeRequest.getMenuId()).get();

        MenuSizeKind menuSizeKind = menuSizeKindRepository.findById(menuSizeRequest.getSizeId()).get();

        MenuSize menuSize = new MenuSize(menu, menuSizeKind, menuSizeRequest.getPrice());

        menuSizeRepositroy.save(menuSize);

        return new BasicVo(ReturnStatus.success);
    }

    public BasicVo deleteMenuSize(long menuSizeId) {
        MenuSize menuSize = menuSizeRepositroy.findById(menuSizeId).get();

        menuSizeRepositroy.delete(menuSize);

        return new BasicVo(ReturnStatus.success);
    }

    public MenuSizeDeleteListVo menuSizeListFindBrandId(long brandId) {
        Brand brand =  brandRepository.findById(brandId).get();

        List<BrandMenuKind> brandMenuKinds = brandMenuKindRepository.findByBrand(brand);

        List<Menu> menus = new ArrayList<>();

        for (BrandMenuKind brandMenuKind : brandMenuKinds){
            menus.addAll(menuRepository.findByBrandMenuKind(brandMenuKind));
        }

        List<MenuSize> menuSizes = new ArrayList<>();

        for (Menu menu : menus){
            menuSizes.addAll(menuSizeRepositroy.findByMenu(menu));
        }

        // return new MenuSizesResponse(menuSizes);
        // return new MenuSizesVO(menuSizes);

        List<MenuSizeDeleteVo> menuSizeDeleteVos = new ArrayList<>();

        for (MenuSize menuSize : menuSizes) {
            MenuSizeDeleteVo menuSizeDeleteVo = MenuSizeDeleteVo.builder()
                    .id(menuSize.getId())
                    .menuName(menuSize.getMenu().getName())
                    .menuPrice(FormatUtil.menuPriceFormat(menuSize.getPrice()))
                    .build();

            menuSizeDeleteVos.add(menuSizeDeleteVo);
        }

        return new MenuSizeDeleteListVo(menuSizeDeleteVos);
    }
}
