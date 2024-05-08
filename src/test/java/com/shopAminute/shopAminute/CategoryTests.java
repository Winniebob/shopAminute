package com.shopAminute.shopAminute;

import com.shopAminute.shopAminute.objects.Category;
import com.shopAminute.shopAminute.objects.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class CategoryTests {

    @Test
    void testCategorySettersAndGetters() {

        Category category = new Category();


        category.setId(1);
        assertEquals(1, category.getId());


        category.setName("Electronics");
        assertEquals("Electronics", category.getName());


        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());

        category.setProduct(products);


        assertNotNull(category.getProduct());
        assertEquals(2, category.getProduct().size());
    }

    @Test
    void testCategoryRelationships() {

        Product product1 = new Product();
        Product product2 = new Product();

        Category category = new Category();
        category.setId(1);

        product1.setCategory(category);
        product2.setCategory(category);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        category.setProduct(products);

        assertEquals(category, product1.getCategory());
        assertEquals(category, product2.getCategory());
    }
}