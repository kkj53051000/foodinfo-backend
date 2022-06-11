package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Menu;
import com.kp.foodinfo.domain.MenuSize;
import com.kp.foodinfo.exception.PriceGameMenuNotFondException;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.MenuRepository;
import com.kp.foodinfo.repository.MenuSizeRepositroy;
import com.kp.foodinfo.util.FormatUtil;
import com.kp.foodinfo.vo.PriceGameVo;
import com.kp.foodinfo.vo.PriceGameVoList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {
    private final MenuRepository menuRepository;

    private final MenuSizeRepositroy menuSizeRepositroy;

    private final BrandRepository brandRepository;


    public PriceGameVoList getPriceGameList(int insertPrice) {

        int highPrice = 0;
        int lowPrice = 0;

        // 가격에따라 + - 다르게 적용
        if (insertPrice < 20000) {
            highPrice = insertPrice + 1000;
            lowPrice = insertPrice - 1000;
        } else {
            highPrice = insertPrice + 2000;
            lowPrice = insertPrice - 2000;
        }


        // one sql

        List<Menu> rangeMenus = menuRepository.findByPriceRange(highPrice, lowPrice).get();

        if (rangeMenus.size() == 0) {
            throw new PriceGameMenuNotFondException();
        }

        // 랜덤 인덱스
        List<Integer> menuRandomIndexs = new ArrayList<>();
        while (menuRandomIndexs.size() < 3) {
            int menuRandomIndex = (int) (Math.random() * rangeMenus.size());
            menuRandomIndexs.add(menuRandomIndex);
        }

        List<PriceGameVo> randomPriceGames = new ArrayList<>();

        for (int menuRandomIndex : menuRandomIndexs) {
            Menu menu = rangeMenus.get(menuRandomIndex);
            Brand brand = menu.getBrandMenuKind().getBrand();

            List<MenuSize> menuSizes = menuSizeRepositroy.findByMenu(menu);

            // MenuSize 가 없을 경우.
            if (menuSizes.size() == 0) {
                PriceGameVo priceGameVo = PriceGameVo.builder()
                        .id(menu.getId())
                        .brandId(brand.getId())
                        .brandName(brand.getName())
                        .brandImg(brand.getImg())
                        .menuName(menu.getName())
                        .menuTitle(priceCompare(menu.getName(), insertPrice, menu.getPrice()))
                        .menuImg(menu.getImg())
                        .menuPrice(FormatUtil.menuPriceFormat(menu.getPrice()))
                        .menuSize(null)
                        .build();

                randomPriceGames.add(priceGameVo);
            } else { // MenuSize 가 있을 경우.

                int menuSizeRandomIndex = (int) (Math.random() * menuSizes.size());

                MenuSize menuSize = menuSizes.get(menuSizeRandomIndex);

                Menu tempMenu = menuSize.getMenu();

                Brand tempBrand = tempMenu.getBrandMenuKind().getBrand();

                PriceGameVo priceGameVo = PriceGameVo.builder()
                        .id(menu.getId())
                        .brandId(tempBrand.getId())
                        .brandName(tempBrand.getName())
                        .brandImg(tempBrand.getImg())
                        .menuName(tempMenu.getName())
                        .menuTitle(priceCompare(tempMenu.getName(), insertPrice, menu.getPrice()))
                        .menuImg(tempMenu.getImg())
                        .menuPrice(FormatUtil.menuPriceFormat(tempMenu.getPrice()))
                        .menuSize(menuSize.getMenuSizeKind().getSize())
                        .build();

                randomPriceGames.add(priceGameVo);
            }
        }

        PriceGameVoList priceGameVoList = new PriceGameVoList(randomPriceGames);

        return priceGameVoList;
    }


    String priceCompare(String name, int insertPrice, int menuPrice) {
        if (insertPrice > menuPrice) {
            return name + "을 먹으면 " + FormatUtil.menuPriceFormat(insertPrice - menuPrice) + "원이 남아요! " + "(" + FormatUtil.menuPriceFormat(menuPrice) + "₩)";
        } else if (insertPrice < menuPrice) {
            return FormatUtil.menuPriceFormat(menuPrice - insertPrice) + "원만 얹으면 " + name + "을 먹을 수 있어요! " + "(" + FormatUtil.menuPriceFormat(menuPrice) + "₩)";
        } else {
            return name + "을 먹으면 딱이에요! " + "(" + FormatUtil.menuPriceFormat(menuPrice) + "₩)";
        }
    }
}