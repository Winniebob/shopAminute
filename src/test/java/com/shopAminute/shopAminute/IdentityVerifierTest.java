package com.shopAminute.shopAminute;

import com.shopAminute.shopAminute.objects.Person;
import com.shopAminute.shopAminute.util.IdentityVerifier;
import com.shopAminute.shopAminute.сoordinators.IndividualHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class IdentityVerifierTest {

    private IdentityVerifier identityVerifier;

    @Mock
    private IndividualHandler individualHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        identityVerifier = new IdentityVerifier(individualHandler);
    }

    @Test
    void ShouldNotProduceErrors() {

        Person person = new Person();
        person.setLogin("unique_login");
        Errors errors = new BeanPropertyBindingResult(person, "person");

        when(individualHandler.findByLogin(person)).thenReturn(null);

        identityVerifier.validate(person, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    void ShouldProduceLoginError() {

        Person person = new Person();
        person.setLogin("existing_login");
        Errors errors = new BeanPropertyBindingResult(person, "person");

        when(individualHandler.findByLogin(person)).thenReturn(new Person());

        identityVerifier.validate(person, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("login"));
        assertEquals("login is already in the system", errors.getFieldError("login").getDefaultMessage());
    }
}