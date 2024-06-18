package com.example.interviewPrep.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class RequestContext {
    private String email;
    private String userName;
    private String userId;


    @Override
    public String toString() {
        return "RequestContext{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
