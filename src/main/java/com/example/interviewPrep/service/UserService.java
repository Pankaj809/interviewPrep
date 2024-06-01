package com.example.interviewPrep.service;

import com.example.interviewPrep.model.User;
import com.example.interviewPrep.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveOrUpdateUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            existingUser.setFullName(user.getFullName());
            existingUser.setImageUrl(user.getImageUrl());
            existingUser.setEmail(user.getEmail());
            return userRepository.save(existingUser);
        } else {
            return userRepository.save(user);
        }
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
