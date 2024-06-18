// com.example.interviewPrep.model.PreQuestionSet.java
package com.example.interviewPrep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "PreQuestionSet")
public class PreQuestionSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String university;
    @Enumerated(EnumType.STRING)
    private EnglishProficiencyTest englishProficiencyTest;
    private String interestedCourse;
    private Double gpa;
    @Enumerated(EnumType.STRING)
    private Degree degree;
    @Enumerated(EnumType.STRING)
    private Sponsor sponsor;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public enum EnglishProficiencyTest {
        IELTS, TOEFL, DUOLINGO
    }

    // Enum for Degree
    public enum Degree {
        BACHELORS, MASTERS, PHD
    }

    // Enum for Sponsor
    public enum Sponsor {
        SELF, COMPANY, GOVERNMENT
    }
}