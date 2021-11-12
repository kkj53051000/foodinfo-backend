package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepositoryCustom {
    @Autowired
    EntityManager em;

    @Override
    public List<Item> findMyItems() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
