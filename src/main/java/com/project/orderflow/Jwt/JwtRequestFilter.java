package com.project.orderflow.Jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        // JWT 토큰은 "Bearer "로 시작해야 함
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);  // "Bearer " 이후 토큰 값 추출
            try {
                username = jwtUtil.getUsernameFromToken(jwtToken);
            } catch (Exception e) {
                logger.error("JWT 토큰이 만료되었거나 유효하지 않음", e);
            }
        }

        // 유저명과 SecurityContext의 인증 정보가 없는 경우
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 토큰이 유효한지 확인
            if (jwtUtil.validateToken(jwtToken, username)) {
                // 인증 정보를 설정
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, new ArrayList<>());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}