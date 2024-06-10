package com.example.interviewPrep.service;

import com.example.interviewPrep.model.User;
import com.example.interviewPrep.repository.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<User> findUserById(Long id) throws Exception {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id);
        }
        else {
            throw new Exception("User not found with email id");
        }
    }

    public void deleteUser(Long id) throws Exception {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
        throw new Exception("User not found with email id");
    }

    public List<User> findAllUsers() throws Exception {
        return userRepository.findAll();
    }
}
