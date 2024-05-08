package com.shopAminute.shopAminute;

import com.shopAminute.shopAminute.objects.Category;
import com.shopAminute.shopAminute.objects.Image;
import com.shopAminute.shopAminute.objects.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.Collections;

class ProductTests {

    @Test
    void testProductSettersAndGetters() {
        // Создаем экземпляр Product
        Product product = new Product();

        // Устанавливаем и проверяем id
        product.setId(1);
        assertEquals(1, product.getId());

        // Устанавливаем и проверяем title
        product.setTitle("Smartphone");
        assertEquals("Smartphone", product.getTitle());

        // Устанавливаем и проверяем description
        product.setDescription("Latest model with high specs");
        assertEquals("Latest model with high specs", product.getDescription());

        // Устанавливаем и проверяем price
        product.setPrice(999.99f);
        assertEquals(999.99f, product.getPrice());

        // Устанавливаем и проверяем warehouse
        product.setWarehouse("Warehouse A");
        assertEquals("Warehouse A", product.getWarehouse());

        // Устанавливаем и проверяем seller
        product.setSeller("Seller XYZ");
        assertEquals("Seller XYZ", product.getSeller());

        // Устанавливаем и проверяем category
        Category category = new Category();
        product.setCategory(category);
        assertEquals(category, product.getCategory());

        // Устанавливаем и проверяем dateTime
        LocalDateTime dateTime = LocalDateTime.now();
        product.setDateTime(dateTime);
        assertEquals(dateTime, product.getDateTime());

        // Устанавливаем и проверяем imageList
        Image image = new Image();
        product.addImageToProduct(image);
        assertEquals(Collections.singletonList(image), product.getImageList());
    }

    @Test
    void testProductInitialization() {
        // Создаем экземпляр Product с параметрами конструктора
        Category category = new Category();
        Image image = new Image();
        Product product = new Product("Smartphone", "Latest model with high specs", 999.99f, "Warehouse A", "Seller XYZ", category, LocalDateTime.now(), Collections.singletonList(image));

        // Проверяем, что все поля инициализированы корректно
        assertEquals("Smartphone", product.getTitle());
        assertEquals("Latest model with high specs", product.getDescription());
        assertEquals(999.99f, product.getPrice());
        assertEquals("Warehouse A", product.getWarehouse());
        assertEquals("Seller XYZ", product.getSeller());
        assertEquals(category, product.getCategory());
        assertNotNull(product.getDateTime());
        assertEquals(Collections.singletonList(image), product.getImageList());
    }
}