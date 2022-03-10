package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Menu;
import com.kp.foodinfo.domain.MenuSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface MenuSizeRepositroy extends JpaRepository<MenuSize, Long> {
    long countByMenu(Menu menu);

    List<MenuSize> findByMenu(Menu menu);

    Set<MenuSize> findByMenuIn(List<Menu> menus);
}
