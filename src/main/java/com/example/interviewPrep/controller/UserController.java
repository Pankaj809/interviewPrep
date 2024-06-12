package com.example.interviewPrep.controller;

import com.example.interviewPrep.dto.UserResponse;
import com.example.interviewPrep.jwt.JwtVerify;
import com.example.interviewPrep.model.User;
import com.example.interviewPrep.service.UserService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public UserResponse getUser(@AuthenticationPrincipal OAuth2User principal) throws ChangeSetPersister.NotFoundException {

        if (principal != null) {
            String email = principal.getAttribute("email");
            String fullName = principal.getAttribute("name");
            String imageUrl = principal.getAttribute("picture");

            User user = new User();
            user.setEmail(email);
            user.setFullName(fullName);
            user.setImageUrl(imageUrl);

            User savedUser = userService.saveOrUpdateUser(user);
            String token = userService.generateToken(savedUser);

            return UserResponse.builder()
                    .user(savedUser)
                    .token(token)
                    .build();
        }

        throw new ChangeSetPersister.NotFoundException();
    }


    @GetMapping("/user/delete")
    public void deleteUser(Long id) throws Exception {
        userService.deleteUser(id);
    }

    @GetMapping("/user/{id}")
    public Optional<User> findById(@PathVariable long id) throws Exception {
        return userService.findUserById(id);
    }
    @JwtVerify
    @GetMapping("/user/findAll")
    public List<User> findAll() throws Exception {
        return userService.findAllUsers();
    }
}
