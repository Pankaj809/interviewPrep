package com.example.interviewPrep.controller;

import com.example.interviewPrep.exception.ProfileCreationException;
import com.example.interviewPrep.exception.ProfileNotFoundException;
import com.example.interviewPrep.model.User;
import com.example.interviewPrep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUserProfiles() {
        List<User> userProfiles = userService.getAllUserProfiles();
        return new ResponseEntity<>(userProfiles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserProfileById(@PathVariable Long id) {
        Optional<User> userProfile = userService.getUserProfileById(id);
        return userProfile.map(profile -> new ResponseEntity<>(profile, HttpStatus.OK))
                .orElseThrow(() -> new ProfileNotFoundException("User profile not found with ID: " + id));
    }

    @PostMapping
    public ResponseEntity<User> createUserProfile(@RequestBody User userProfile) {
        try {
            User createdProfile = userService.createUserProfile(userProfile);
            return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
        } catch (ProfileCreationException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserProfile(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedProfile = userService.updateUserProfile(id, user);
            return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        } catch (ProfileNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long id) {
        try {
            boolean isDeleted = userService.deleteUserProfile(id);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (ProfileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(ProfileNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProfileCreationException.class)
    public ResponseEntity<String> handleCreationException(ProfileCreationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

