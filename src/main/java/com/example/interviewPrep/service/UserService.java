package com.example.interviewPrep.service;

import com.example.interviewPrep.exception.ProfileCreationException;
import com.example.interviewPrep.exception.ProfileNotFoundException;
import com.example.interviewPrep.model.User;
import com.example.interviewPrep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    @Autowired
    public UserService(UserRepository userRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
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

    public User registerUser(User user) {
        user.setVerificationToken(UUID.randomUUID().toString());
        user.setIsVerified(false);
        userRepository.save(user);

        sendVerificationEmail(user);
        return user;
    }

    public void sendVerificationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Email Verification");
        message.setText("To verify your email, please use the following token: " + user.getVerificationToken());

        javaMailSender.send(message);
    }

    public boolean verifyUser(String token) {
        User user = userRepository.findByVerificationToken(token);
        if (user != null && !user.getIsVerified()) {
            user.setIsVerified(true);
            user.setVerificationToken(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
