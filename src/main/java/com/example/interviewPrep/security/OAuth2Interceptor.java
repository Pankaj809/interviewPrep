package com.example.interviewPrep.security;

import com.example.interviewPrep.model.RequestContext;
import com.example.interviewPrep.model.RequestContextHolder;
import com.example.interviewPrep.jwt.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class OAuth2Interceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public OAuth2Interceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
            Map<String, Object> attributes = oauth2Token.getPrincipal().getAttributes();

            String email = (String) attributes.get("email");
            String userName = (String) attributes.get("name");
            String userId = (String) attributes.get("sub"); // 'sub' is commonly used for user ID in OAuth2

            RequestContext context = new RequestContext(email, userName, userId);
            RequestContextHolder.setRequestContext(context);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestContextHolder.clear();
    }
}
