package com.quiz.online.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.online.entity.Quiz;
@Repository
public interface QuizRepository extends JpaRepository<Quiz, String> {
    Quiz findByTitle(String title);
    List<Quiz> findAllByOrderByCreatedAtAsc();
}
