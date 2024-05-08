package com.shopAminute.shopAminute.dataManagers;

import com.shopAminute.shopAminute.objects.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    List<Product> findByTitleContainingIgnoreCase(String name);
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3)", nativeQuery = true)
    List<Product> findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(String title, float ot, float Do);
    @Query(value = "select * from product where (lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1') and (price >= ?2 and price <= ?3) order by price", nativeQuery = true)
    List<Product> findByTitleOrderByPriceAsc(String title, float ot, float Do);

    @Query(value = "select * from product where (lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1') and (price >= ?2 and price <= ?3) order by price desc", nativeQuery = true)
    List<Product> findByTitleOrderByPriceDesc(String title, float ot, float Do);

    @Query(value = "select * from product where category_id = ?4 and(lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1') and (price >= ?2 and price <= ?3) order by price", nativeQuery = true)
    List<Product> findByTitleAndCategoryOrderByPriceAsc(String title, float ot, float Do, int category);

    @Query(value = "select * from product where category_id = ?4 and(lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1') and (price >= ?2 and price <= ?3) order by price desc", nativeQuery = true)
    List<Product> findByTitleAndCategoryOrderByPriceDesc(String title, float ot, float Do, int category);
}