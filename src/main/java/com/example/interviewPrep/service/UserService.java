package com.example.interviewPrep.service;

import com.example.interviewPrep.exception.ProfileCreationException;
import com.example.interviewPrep.exception.ProfileNotFoundException;
import com.example.interviewPrep.model.User;
import com.example.interviewPrep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUserProfiles() {
        return userRepository.findAll();
    }

    public Optional<User> getUserProfileById(Long id) {
        return userRepository.findById(id);
    }

    public User createUserProfile(User userProfile) {
        try {
            return userRepository.save(userProfile);
        } catch (Exception ex) {
            throw new ProfileCreationException("Error creating user profile: " + ex.getMessage());
        }
    }

    public User updateUserProfile(Long id, User userProfile) {
        if (userRepository.existsById(id)) {
            userProfile.setId(id);
            return userRepository.save(userProfile);
        } else {
            throw new ProfileNotFoundException("User profile not found with ID: " + id);
        }
    }

    public boolean deleteUserProfile(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            throw new ProfileNotFoundException("User profile not found with ID: " + id);
        }
    }
}
