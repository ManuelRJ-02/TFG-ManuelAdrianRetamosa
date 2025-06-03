/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.security;

import edu.dwes.pi_manuelRetamosa_backend.services.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author manue
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final JpaUserDetailsService userDetailsService;

    public SpringSecurityConfig(JpaUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
                .requestMatchers("/users/register").permitAll()
                .requestMatchers("/users/login").permitAll()
                .requestMatchers("/products").permitAll()
                .requestMatchers("/productVariants/**").permitAll()
                .requestMatchers("/cartProducts/**").permitAll()
                .requestMatchers("/cartShoppings/**").permitAll()
                .requestMatchers("/detailOrders/**").permitAll()
                .requestMatchers("/orders/**").permitAll()
                .requestMatchers("/contact/**").permitAll()
                .requestMatchers("/concerts/**").permitAll()
                .requestMatchers("/songs/**").permitAll()
                .requestMatchers("/albums/**").permitAll()
                .requestMatchers("/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .userDetailsService(userDetailsService)
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}