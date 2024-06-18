// com.example.interviewPrep.repository.PreQuestionSetRepository.java
package com.example.interviewPrep.repository;

import com.example.interviewPrep.model.PreQuestionSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PreQuestionSetRepository extends JpaRepository<PreQuestionSet, Long> {
}