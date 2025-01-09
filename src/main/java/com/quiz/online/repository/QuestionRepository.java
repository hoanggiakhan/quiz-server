package com.quiz.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.online.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    
}
