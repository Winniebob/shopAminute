package com.shopAminute.shopAminute;

import com.shopAminute.shopAminute.objects.Person;
import com.shopAminute.shopAminute.security.PersonDetails;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;

class PersonDetailsTest {

    @Test
    void testPersonDetails() {

        Person person = new Person();
        person.setLogin("userLogin");
        person.setPassword("userPassword");
        person.setRole("USER_ROLE");


        PersonDetails personDetails = new PersonDetails(person);

        assertEquals(person, personDetails.getPerson());

        Collection<? extends GrantedAuthority> authorities = personDetails.getAuthorities();
        assertTrue(authorities.contains(new SimpleGrantedAuthority("USER_ROLE")));

        assertEquals("userPassword", personDetails.getPassword());

        assertEquals("userLogin", personDetails.getUsername());

        assertTrue(personDetails.isAccountNonExpired());

        assertTrue(personDetails.isAccountNonLocked());

        assertTrue(personDetails.isCredentialsNonExpired());

        assertTrue(personDetails.isEnabled());
    }
}