package com.example.interviewPrep.controller;

import com.example.interviewPrep.model.User;
import com.example.interviewPrep.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public User getUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            String email = principal.getAttribute("email");
            String fullName = principal.getAttribute("name");
            String imageUrl = principal.getAttribute("picture");
            User user = new User();
            user.setEmail(email);
            user.setFullName(fullName);
            user.setImageUrl(imageUrl);
            return userService.saveOrUpdateUser(user);
        }
        return null;
    }

    @GetMapping("/user/delete")
    public void deleteUser(Long id) throws Exception {
            userService.deleteUser(id);
    }

    @GetMapping("/user/findById")
    public Optional<User> findById(Long id) throws Exception {
        return userService.findUserById(id);
    }

    @GetMapping("/user/findAll")
    public List<User> findAll() throws Exception {
        return userService.findAllUsers();
    }

}
