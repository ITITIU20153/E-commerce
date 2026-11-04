package com.example.demo.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    public  SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                 // Chỉ ADMIN
                .requestMatchers("/admin/**")
                .hasRole("ADMIN")

                .requestMatchers("/cart/**")
                .hasRole("CUSTOMER")

                // // User đã đăng nhập
                // .requestMatchers("/api/users/**")
                //     .hasAnyRole("USER")
                .requestMatchers("/auth/**",
                "/orders/**").permitAll()

                // Các API còn lại
                .anyRequest().authenticated()
            )
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )
            .addFilterBefore(
                jwtAuthenticationFilter, 
            UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
