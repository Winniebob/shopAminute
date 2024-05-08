package com.shopAminute.shopAminute;

import com.shopAminute.shopAminute.objects.Basket;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BasketTests {

    @Test
    void testBasketConstructors() {
        Basket defaultBasket = new Basket();
        assertNotNull(defaultBasket);

        Basket basketWithParams = new Basket(1, 2);
        assertNotNull(basketWithParams);
        assertEquals(1, basketWithParams.getPersonId());
        assertEquals(2, basketWithParams.getProductId());
    }

    @Test
    void testSettersAndGetters() {
        Basket basket = new Basket();
        basket.setId(10);
        basket.setPersonId(20);
        basket.setProductId(30);

        assertEquals(10, basket.getId());
        assertEquals(20, basket.getPersonId());
        assertEquals(30, basket.getProductId());
    }


    @Test
    void testHashCode() {
        Basket basket = new Basket(1, 2);
        int hashCode = basket.hashCode();
        assertNotNull(hashCode);
    }
}