package com.shopAminute.shopAminute.util;

import com.shopAminute.shopAminute.objects.Person;
import com.shopAminute.shopAminute.сoordinators.IndividualHandler;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class IdentityVerifier implements Validator {

    private final IndividualHandler individualHandler;

    public IdentityVerifier(IndividualHandler personService) {
        this.individualHandler = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (individualHandler.findByLogin(person) != null) {
            errors.rejectValue("login", "", "login is already in the system");
        }
    }
}