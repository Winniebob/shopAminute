package com.shopAminute.shopAminute;

import com.shopAminute.shopAminute.dataManagers.IndividualStorage;
import com.shopAminute.shopAminute.objects.Person;
import com.shopAminute.shopAminute.сoordinators.IdentityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IdentityServiceTest {

    @Mock
    private IndividualStorage individualStorage;

    @InjectMocks
    private IdentityService identityService;

    @Test
    void loadUserByUsername() {

        String username = "existing_user";
        Person mockPerson = new Person();
        mockPerson.setLogin(username);
        when(individualStorage.findByLogin(username)).thenReturn(Optional.of(mockPerson));
        UserDetails userDetails = identityService.loadUserByUsername(username);
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    void ShouldThrowUsernameNotFoundException() {
        String username = "non_existing_user";
        when(individualStorage.findByLogin(username)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> {
            identityService.loadUserByUsername(username);
        });
    }
}