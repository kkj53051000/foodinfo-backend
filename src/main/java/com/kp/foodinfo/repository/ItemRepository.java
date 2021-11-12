package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
    @Query("select i from Item i")
    Page<Item> findByPage(PageRequest pageRequest);

    @Query("select i from Item i")
    Slice<Item> findBySlice(PageRequest pageRequest);
}
