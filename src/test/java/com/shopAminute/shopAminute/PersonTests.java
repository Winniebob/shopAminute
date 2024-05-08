package com.shopAminute.shopAminute;

import com.shopAminute.shopAminute.objects.Person;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonTests {

    @Test
    void testPersonConstructors() {
        Person personDefault = new Person();
        assertNotNull(personDefault);

        Person personWithLoginRole = new Person("admin", "ROLE_ADMIN");
        assertNotNull(personWithLoginRole);

        Person personWithRole = new Person("ROLE_USER");
        assertNotNull(personWithRole);

        Person personWithId = new Person(1);
        assertNotNull(personWithId);

    }

    @Test
    void testSettersAndGetters() {
        Person person = new Person();
        person.setId(1);
        person.setLogin("UserLogin");
        person.setPassword("UserPassword");
        person.setRole("ROLE_USER");

        assertEquals(1, person.getId());
        assertEquals("UserLogin", person.getLogin());
        assertEquals("UserPassword", person.getPassword());
        assertEquals("ROLE_USER", person.getRole());
    }

    @Test
    void testEqualsAndHashCode() {
        Person person1 = new Person();
        person1.setId(1);
        person1.setLogin("UserLogin");
        person1.setPassword("UserPassword");

        Person person2 = new Person();
        person2.setId(1);
        person2.setLogin("UserLogin");
        person2.setPassword("UserPassword");

        assertEquals(person1, person2);
        assertEquals(person1.hashCode(), person2.hashCode());

        Person person3 = new Person();
        person3.setId(2);
        person3.setLogin("AnotherLogin");
        person3.setPassword("AnotherPassword");

        assertNotEquals(person1, person3);
        assertNotEquals(person1.hashCode(), person3.hashCode());
    }


}