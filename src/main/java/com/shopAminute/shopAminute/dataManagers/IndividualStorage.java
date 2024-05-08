package com.shopAminute.shopAminute.dataManagers;

import com.shopAminute.shopAminute.objects.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndividualStorage extends JpaRepository<Person, Integer> {
    Optional<Person> findByLogin(String login);
}