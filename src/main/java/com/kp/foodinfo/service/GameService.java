package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Menu;
import com.kp.foodinfo.domain.MenuSize;
import com.kp.foodinfo.repository.MenuRepository;
import com.kp.foodinfo.repository.MenuSizeRepositroy;
import com.kp.foodinfo.util.FormatUtil;
import com.kp.foodinfo.vo.PriceGameVo;
import com.kp.foodinfo.vo.PriceGameVoList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {
    private final MenuRepository menuRepository;

    private final MenuSizeRepositroy menuSizeRepositroy;


    public PriceGameVoList getPriceGameList(int insertPrice) {

        List<Menu> menus = menuRepository.findAll();

        int priceHigh = insertPrice + 1000;
        int priceLow = insertPrice - 1000;

        // 가격에따라 + - 다르게 적용

        List<Menu> tempMenus = new ArrayList<>();
        List<Long> tempMenuIds = new ArrayList<>();

        // 메뉴 중복 검사
        for (Menu menu : menus) {
            if(menu.getPrice() >= priceLow && menu.getPrice() <= priceHigh) {
                tempMenus.add(menu);
                tempMenuIds.add(menu.getId());
            }
        }

        if(tempMenus.size() == 0) {
            // 예외 처리
        }

        // tempMenus에서 primaryId 기준 랜덤 3개 뽑아야함.

        List<PriceGameVo> randomPriceGames = new ArrayList<>();

        while (randomPriceGames.size() < 3) {
            int menuRandomIndex = (int)(Math.random() * tempMenus.size());

            Menu menu = tempMenus.get(menuRandomIndex);

            for(int i = 0; i < randomPriceGames.size(); i++) {
                if (randomPriceGames.get(i).getMenuName().equals(menu.getName())){
                    break;
                }
            }

            Brand brand = menu.getBrandMenuKind().getBrand();

            List<MenuSize> menuSizes = menuSizeRepositroy.findByMenu(menu);



            if(menuSizes.size() == 0) {
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
            }else{

                int menuSizeRandomIndex = (int)(Math.random() * menuSizes.size());

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
        if(insertPrice > menuPrice) {
            return name + "을 먹으면 " + FormatUtil.menuPriceFormat(insertPrice - menuPrice) + "원이 남아요!";
        }else if (insertPrice < menuPrice){
            return FormatUtil.menuPriceFormat(menuPrice - insertPrice) + "원만 얹으면 " + name + "을 먹을 수 있어요!";
        }else{
            return name + "을 먹으면 딱이에요!";
        }
    }
}