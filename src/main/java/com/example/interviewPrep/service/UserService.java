package com.example.interviewPrep.service;

import com.example.interviewPrep.model.User;
import com.example.interviewPrep.repository.UserRepository;
import com.example.interviewPrep.jwt.JwtUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtil;

    public UserService(UserRepository userRepository, JwtUtils jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
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

    public String generateToken(User user) {
        return jwtUtil.generateToken(user);
    }

    public Optional<User> findUserById(Long id) throws Exception {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id);
        } else {
            throw new Exception("User not found with email id");
        }
    }

    public void deleteUser(Long id) throws Exception {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new Exception("User not found with email id");
        }
    }

    public List<User> findAllUsers() throws Exception {
        return userRepository.findAll();
    }
}
