package com.shopAminute.shopAminute;

import com.shopAminute.shopAminute.dataManagers.IndividualStorage;
import com.shopAminute.shopAminute.objects.Person;
import com.shopAminute.shopAminute.сoordinators.IndividualHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IndividualHandlerTest {

    @Mock
    private IndividualStorage individualStorage;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private IndividualHandler individualHandler;

    @Test
    void ShouldReturnUser() {
        Person mockPerson = new Person();
        mockPerson.setLogin("user_login");
        Optional<Person> optionalPerson = Optional.of(mockPerson);
        when(individualStorage.findByLogin(anyString())).thenReturn(optionalPerson);
        Person result = individualHandler.findByLogin(mockPerson);
        assertNotNull(result);
        assertEquals("user_login", result.getLogin());
    }

    @Test
    void ShouldReturnNull() {
        Person mockPerson = new Person();
        mockPerson.setLogin("user_login");
        when(individualStorage.findByLogin(anyString())).thenReturn(Optional.empty());
        Person result = individualHandler.findByLogin(mockPerson);
        assertNull(result);
    }

    @Test
    void ShouldEncodePasswordAndSaveUser() {
        Person mockPerson = new Person();
        mockPerson.setPassword("plain_password");
        mockPerson.setLogin("user_login");
        String encodedPassword = "encoded_password";
        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);

        individualHandler.register(mockPerson);

        verify(passwordEncoder).encode("plain_password");
        verify(individualStorage).save(mockPerson);
        assertEquals("encoded_password", mockPerson.getPassword());
        assertEquals("ROLE_USER", mockPerson.getRole());
    }
}