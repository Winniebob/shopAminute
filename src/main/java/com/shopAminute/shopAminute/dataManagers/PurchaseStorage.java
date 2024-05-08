package com.shopAminute.shopAminute.dataManagers;

import com.shopAminute.shopAminute.objects.Order;
import com.shopAminute.shopAminute.objects.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseStorage extends JpaRepository<Order, Integer> {
    List<Order> findByPerson(Person person);
}