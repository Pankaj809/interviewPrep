package com.example.interviewPrep.jwt;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

public @interface JwtVerify {
}

@Aspect
@Component
class JwtVerifyAspect {

    @Before("@annotation(com.example.interviewPrep.jwt.JwtVerify)")
    public void verifyJwt() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new SecurityException("Unauthorized");
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails == null || !userDetails.isEnabled()) {
            throw new SecurityException("Unauthorized");
        }
    }
}