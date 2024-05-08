package com.shopAminute.shopAminute.dataManagers;

import com.shopAminute.shopAminute.objects.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationStorage extends JpaRepository<Category, Integer> {
    com.shopAminute.shopAminute.objects.Category findByName(String name);
}