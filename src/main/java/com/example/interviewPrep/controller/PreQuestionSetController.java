package com.example.interviewPrep.controller;

import com.example.interviewPrep.model.PreQuestionSet;
import com.example.interviewPrep.service.PreQuestionSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class PreQuestionSetController {

    private final PreQuestionSetService preQuestionSetService;

    @Autowired
    public PreQuestionSetController(PreQuestionSetService preQuestionSetService) {
        this.preQuestionSetService = preQuestionSetService;
    }

    @PostMapping("/pre-questions")
    public ResponseEntity<PreQuestionSet> createPreQuestionSet(@RequestBody PreQuestionSet preQuestionSet, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7); // Remove "Bearer " prefix
        PreQuestionSet createdPreQuestionSet = preQuestionSetService.createPreQuestionSet(preQuestionSet, token);
        return ResponseEntity.ok(createdPreQuestionSet);
    }
}
