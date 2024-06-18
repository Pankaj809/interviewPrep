package com.example.interviewPrep.service;

import com.example.interviewPrep.jwt.JwtUtil;
import com.example.interviewPrep.model.User;
import com.example.interviewPrep.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public User saveOrUpdateUser(User user) {
        return userRepository.findByEmail(user.getEmail())
                .map(existingUser -> {
                    existingUser.setFullName(user.getFullName());
                    existingUser.setImageUrl(user.getImageUrl());
                    return userRepository.save(existingUser);
                })
                .orElseGet(() -> userRepository.save(user));
    }

    public String generateToken(User user) {
        return jwtUtil.generateToken(user.getFullName(), user.getEmail(), String.valueOf(user.getId()));
    }

    public Optional<User> findUserById(Long id) throws Exception {
        return Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found with id: " + id)));
    }

    public void deleteUser(Long id) throws Exception {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new Exception("User not found with id: " + id);
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
