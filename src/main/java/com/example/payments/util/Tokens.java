package com.example.payments.util;

import com.example.payments.components.JwtFilter;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.util.StringUtils.hasText;

public class Tokens {

    public static final String AUTHORIZATION = "Authorization";

    private Tokens() {
    }

    public static String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
