package com.smartretails.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.smartretails.backend.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 🔓 Authentication APIs — open to everyone
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/signup").hasRole("ADMIN")

                        // 🛍️ Product APIs
                        .requestMatchers(HttpMethod.GET, "/api/products/search-products", "/api/products/{id}")
                        .hasAnyRole("ADMIN", "MANAGER", "CASHIER") // view products
                        .requestMatchers(HttpMethod.GET, "/api/products/**")
                        .hasAnyRole("ADMIN", "MANAGER","CASHIER")
                        .requestMatchers(HttpMethod.POST, "/api/products/**")
                        .hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/products/**")
                        .hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/products/**")
                        .hasRole("ADMIN")

                        // Stock / Inventory APIs
                        .requestMatchers("/api/stock/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        // Purchase Order & Order Item APIs
                        .requestMatchers("/api/purchase-orders/**", "/api/purchase-order-items/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        // Supplier APIs
                        .requestMatchers("/api/suppliers/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        // Sales & Sales Item APIs
                        .requestMatchers(HttpMethod.POST, "/api/sales/**")
                        .hasAnyRole("ADMIN", "MANAGER", "CASHIER") // for creating sales
                        .requestMatchers(HttpMethod.GET, "/api/sales/**")
                        .hasAnyRole("ADMIN", "MANAGER") // for viewing reports

                        // User Management APIs
                        .requestMatchers("/api/users/**")
                        .hasRole("ADMIN")

                        // Reports
                        .requestMatchers("/api/reports/**")
                        .hasAnyRole("ADMIN", "MANAGER","CASHIER")

                        // Default rule — all others must be authenticated
                        .anyRequest().authenticated())

                .userDetailsService(customUserDetailsService)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
