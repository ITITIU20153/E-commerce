package com.example.demo.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtTokenProvider jwtTokenProvider,
            UserDetailsService userDetailsService) {

        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        // System.out.println("Start Jwt Filter");
        // 1. Lấy Authorization Header
        String authHeader = request.getHeader("Authorization");

        // 2. Kiểm tra Bearer Token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        //     System.out.println("Token is invalid");
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Lấy JWT từ Header
        String token = authHeader.substring(7);
        // System.out.println(token);
        // 4. Kiểm tra JWT và xác thực user
        try {
            if (jwtTokenProvider.validateToken(token)
                    && SecurityContextHolder.getContext()
                            .getAuthentication() == null) {
                // System.out.println("Validation Token");
                // 5. Lấy username từ JWT
                String username =
                        jwtTokenProvider.getUsernameFromToken(token);

                // 6. Lấy thông tin user
                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(username);
                
                // 7. Tạo Authentication
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                // 8. Lưu Authentication vào SecurityContext
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            } 
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            System.out.println(e.getMessage());
        }

        // 9. Tiếp tục Filter Chain
        filterChain.doFilter(request, response);
    }
}