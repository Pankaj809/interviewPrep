package com.example.interviewPrep.security;

import com.example.interviewPrep.jwt.JwtUtil;
import com.example.interviewPrep.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    private final OAuth2Interceptor oAuth2Interceptor;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public SecurityConfig(OAuth2Interceptor oAuth2Interceptor, UserService userService, JwtUtil jwtUtil) {
        this.oAuth2Interceptor = oAuth2Interceptor;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/user/pre-questions").authenticated()// Provide full access to this endpoint
                                .anyRequest().permitAll() // Secure all other endpoints
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oAuth2SuccessHandler())
                );

//                .csrf().disable(); // Disable CSRF protection if not needed (optional, depending on your use case)

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(userService, jwtUtil);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(oAuth2Interceptor).addPathPatterns("/**");
    }
}
