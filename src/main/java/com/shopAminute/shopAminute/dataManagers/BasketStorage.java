package com.shopAminute.shopAminute.dataManagers;

import com.shopAminute.shopAminute.objects.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface BasketStorage extends JpaRepository<Basket, Integer> {
    List<Basket> findByPersonId(int id);

    @Modifying
    @Query(value = "delete from product_cart where product_id=?1", nativeQuery = true)
    void deleteCartByProductId(int id);
}