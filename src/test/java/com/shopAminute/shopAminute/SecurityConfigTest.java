package com.shopAminute.shopAminute;

import com.shopAminute.shopAminute.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.Assert.assertTrue;

class SecurityConfigTest {

    @Test
    void getPasswordEncoder() {
        SecurityConfig securityConfig = new SecurityConfig(null);
        PasswordEncoder passwordEncoder = securityConfig.getPasswordEncode();

        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }



}