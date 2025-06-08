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
            .cors(withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
                .requestMatchers(HttpMethod.POST, "/users/register/**", "/users/login/**").permitAll() 
                .requestMatchers(HttpMethod.GET,"/products/**", "/songs/**", "/albums/**", "/concerts/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/addresses/**","/cartProducts/**", "/cartShoppings/**", "/creditCards/**","/detailOrders/**", "/orders/**",
                        "/productVariants/**", "/creditCards/**").hasAuthority("ROLE_USER_REGISTERED")
                .requestMatchers(HttpMethod.POST,"/addresses/**", "/productVariants/crear", "/cartProducts/**", "/orders/**", 
                        "/detailOrders/**", "/creditCards/**").hasAuthority("ROLE_USER_REGISTERED")
                .requestMatchers(HttpMethod.POST,"/contacts/**").permitAll()
                .requestMatchers(HttpMethod.DELETE,"/addresses/**", "/cartProducts/**").hasAuthority("ROLE_USER_REGISTERED")
                .requestMatchers(HttpMethod.PUT,"/users/**", "/cartProducts/**").hasAuthority("ROLE_USER_REGISTERED")
                .requestMatchers(HttpMethod.GET, "/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/**").hasAuthority("ROLE_ADMIN")
                    
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