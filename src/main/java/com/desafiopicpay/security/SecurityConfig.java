package com.desafiopicpay.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)// Deshabilita CSRF (solo para desarrollo)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // Permite todos los endpoints sin autenticación
                );
        return http.build();
    }
}

