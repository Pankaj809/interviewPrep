package com.example.interviewPrep.security;

import com.example.interviewPrep.jwt.JwtUtil;
import com.example.interviewPrep.model.User;
import com.example.interviewPrep.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public OAuth2SuccessHandler(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String fullName = oAuth2User.getAttribute("name");
        String imageUrl = oAuth2User.getAttribute("picture");

        User user = new User();
        user.setEmail(email);
        user.setFullName(fullName);
        user.setImageUrl(imageUrl);

        User savedUser = userService.saveOrUpdateUser(user);
        String token = jwtUtil.generateToken(savedUser.getFullName(), savedUser.getEmail(), String.valueOf(savedUser.getId()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Redirect or respond with the token and user details
        response.sendRedirect("/loginSuccess?token=" + token);
    }
}
