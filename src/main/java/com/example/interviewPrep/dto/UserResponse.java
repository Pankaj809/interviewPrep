package com.example.interviewPrep.dto;

import com.example.interviewPrep.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private User user;
    private String token;
}
