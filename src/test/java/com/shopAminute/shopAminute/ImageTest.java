package com.shopAminute.shopAminute;

import com.shopAminute.shopAminute.objects.Image;
import com.shopAminute.shopAminute.objects.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ImageTest {

    @Test
    void testImageSettersAndGetters() {

        Product product = new Product();


        Image image = new Image(1, "image.jpg", product);


        assertEquals(1, image.getId());
        assertEquals("image.jpg", image.getFileName());
        assertEquals(product, image.getProduct());


        image.setId(2);
        image.setFileName("newImage.jpg");

        Product newProduct = new Product();
        image.setProduct(newProduct);


        assertEquals(2, image.getId());
        assertEquals("newImage.jpg", image.getFileName());
        assertEquals(newProduct, image.getProduct());
    }

    @Test
    void testImageConstructor() {
        Product product = new Product();
        Image image = new Image(1, "image.jpg", product);

        assertEquals(1, image.getId());
        assertEquals("image.jpg", image.getFileName());
        assertEquals(product, image.getProduct());
    }

    @Test
    void testImageNoArgsConstructor() {
        Image image = new Image();

        assertEquals(0, image.getId());
        assertNull(image.getFileName());
        assertNull(image.getProduct());
    }
}