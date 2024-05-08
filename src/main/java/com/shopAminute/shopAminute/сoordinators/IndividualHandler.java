package com.shopAminute.shopAminute.сoordinators;

import com.shopAminute.shopAminute.objects.Person;
import com.shopAminute.shopAminute.dataManagers.IndividualStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class IndividualHandler {
    private final IndividualStorage individualStorage;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public IndividualHandler(IndividualStorage individualStorage, PasswordEncoder passwordEncoder) {
        this.individualStorage = individualStorage;
        this.passwordEncoder = passwordEncoder;
    }

    public Person findByLogin(Person person) {
        Optional<Person> person_db = individualStorage.findByLogin(person.getLogin());
        return person_db.orElse(null);
    }

    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        individualStorage.save(person);
    }
}