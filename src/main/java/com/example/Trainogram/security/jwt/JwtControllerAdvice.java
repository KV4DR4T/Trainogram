package com.example.Trainogram.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class JwtControllerAdvice {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @ModelAttribute
    public JwtUser getJwtUser(WebRequest request) {
        String token = request.getHeader("Authorization");
        JwtUser jwtUser = null;
        if (token != null) {
            jwtUser = (JwtUser) jwtTokenProvider.getAuthentication(jwtTokenProvider.resolveToken((HttpServletRequest) request)).getPrincipal();
        }
        return jwtUser;
    }
}
