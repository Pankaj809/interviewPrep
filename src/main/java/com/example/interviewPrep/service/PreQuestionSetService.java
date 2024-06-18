package com.example.interviewPrep.service;

import com.example.interviewPrep.jwt.JwtUtil;
import com.example.interviewPrep.model.PreQuestionSet;
import com.example.interviewPrep.model.User;
import com.example.interviewPrep.repository.PreQuestionSetRepository;
import com.example.interviewPrep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PreQuestionSetService {

    private final PreQuestionSetRepository preQuestionSetRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtils;

    @Autowired
    public PreQuestionSetService(PreQuestionSetRepository preQuestionSetRepository, UserRepository userRepository, JwtUtil jwtUtils) {
        this.preQuestionSetRepository = preQuestionSetRepository;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    public PreQuestionSet createPreQuestionSet(PreQuestionSet preQuestionSet, String token) {
        String email = jwtUtils.extractEmail(token);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with the token provided"));

        PreQuestionSet newPreQuestionSet = PreQuestionSet.builder()
                .name(preQuestionSet.getName())
                .university(preQuestionSet.getUniversity())
                .englishProficiencyTest(preQuestionSet.getEnglishProficiencyTest())
                .interestedCourse(preQuestionSet.getInterestedCourse())
                .gpa(preQuestionSet.getGpa())
                .degree(preQuestionSet.getDegree())
                .sponsor(preQuestionSet.getSponsor())
                .user(user)
                .build();

        return preQuestionSetRepository.save(newPreQuestionSet);
    }
}
