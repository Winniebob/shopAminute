package com.shopAminute.shopAminute.security;

import com.shopAminute.shopAminute.сoordinators.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final IdentityService personDetailsService;

    @Bean
    public PasswordEncoder getPasswordEncode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/authentication", "/registration", "/error", "/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/product", "/product/info/{id}", "/product/search").permitAll().anyRequest().hasAnyRole("USER", "ADMIN").and() // указываем что дальше настраиватеся аутентификация и соединяем ее с настройкой доступа
                .formLogin().loginPage("/authentication")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/person account", true)
                .failureUrl("/authentication?error")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/authentication");
        return http.build();
    }


    @Autowired
    public SecurityConfig(IdentityService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }


    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(personDetailsService).passwordEncoder(getPasswordEncode());
    }

}


