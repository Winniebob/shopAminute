package com.shopAminute.shopAminute;

import com.shopAminute.shopAminute.objects.Order;
import com.shopAminute.shopAminute.objects.Person;
import com.shopAminute.shopAminute.objects.Product;
import com.shopAminute.shopAminute.settings.Status;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class OrderTests {

    @Test
    void testOrderSettersAndGetters() {
        // Создаем экземпляры зависимых сущностей
        Product product = new Product(/* параметры конструктора */);
        Person person = new Person(/* параметры конструктора */);
        Status status = Status.Принят; // предполагаем, что Status - это enum


        Order order = new Order("12345", product, person, 10, 100.0f, status);


        assertEquals("12345", order.getNumber());
        assertEquals(product, order.getProduct());
        assertEquals(person, order.getPerson());
        assertEquals(10, order.getCount());
        assertEquals(100.0f, order.getPrice());
        assertEquals(status, order.getStatus());

        order.setNumber("54321");
        order.setCount(20);
        order.setPrice(200.0f);
        order.setStatus(Status.Получен);


        assertEquals("54321", order.getNumber());
        assertEquals(20, order.getCount());
        assertEquals(200.0f, order.getPrice());
        assertEquals(Status.Получен, order.getStatus());
    }

    @Test
    void testOrderPrePersist() {
        Order order = new Order();
        assertNull(order.getDateTime());

        order.init();

        assertNotNull(order.getDateTime());
        assertTrue(order.getDateTime().isBefore(LocalDateTime.now().plusSeconds(1)));
    }
    @Test
    void testOrderDefaultConstructor() {
        Order order = new Order();
        assertNotNull(order);
    }

    @Test
    void testOrderFields() {
        Order order = new Order();
        order.setId(1);
        order.setNumber("12345");
        Product product = new Product();
        order.setProduct(product);
        Person person = new Person();
        order.setPerson(person);
        order.setCount(5);
        order.setPrice(150.0f);
        LocalDateTime dateTime = LocalDateTime.now();
        order.setDateTime(dateTime);
        Status status = Status.Оформлен;
        order.setStatus(status);

        assertEquals(1, order.getId());
        assertEquals("12345", order.getNumber());
        assertEquals(product, order.getProduct());
        assertEquals(person, order.getPerson());
        assertEquals(5, order.getCount());
        assertEquals(150.0f, order.getPrice());
        assertEquals(dateTime, order.getDateTime());
        assertEquals(status, order.getStatus());
    }


    @Test
    void testOrderNotEquals() {
        Order order1 = new Order("12345", new Product(), new Person(), 10, 100.0f, Status.Принят);
        Order order2 = new Order("54321", new Product(), new Person(), 20, 200.0f, Status.Получен);
        assertNotEquals(order1, order2);
    }

    @Test
    void testOrderHashCode() {
        Order order = new Order("12345", new Product(), new Person(), 10, 100.0f, Status.Принят);
        int hashCode = order.hashCode();
        assertNotNull(hashCode);
    }
}