package com.kp.foodinfo.repository;


import com.kp.foodinfo.domain.BrandMenuKind;
import com.kp.foodinfo.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByBrandMenuKind(BrandMenuKind brandMenuKind);

    Optional<Menu> findByName(String name);

    int countByNameAndBrandMenuKind(String name, BrandMenuKind brandMenuKind);

    Optional<Menu> findByNameAndBrandMenuKind(String name, BrandMenuKind brandMenuKind);

    @Query("select m from Menu m where m.price <= :highPrice and m.price >= :lowPrice")
    Optional<List<Menu>> findByPriceRange(int highPrice, int lowPrice);




}
