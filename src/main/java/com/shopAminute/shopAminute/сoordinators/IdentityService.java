package com.shopAminute.shopAminute.сoordinators;

import com.shopAminute.shopAminute.objects.Person;
import com.shopAminute.shopAminute.dataManagers.IndividualStorage;
import com.shopAminute.shopAminute.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdentityService implements UserDetailsService {
    private final IndividualStorage individualStorage;


    @Autowired
    public IdentityService(IndividualStorage individualStorage) {
        this.individualStorage = individualStorage;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = individualStorage.findByLogin(username);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("Not person with name");
        }
        return new PersonDetails(person.get());
    }
}