package com.quiz.online.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.online.entity.Quiz;
import com.quiz.online.entity.Result;
import com.quiz.online.entity.User;
@Repository
public interface ResultRepository extends JpaRepository<Result, String> {
    List<Result> findByUser(User user);
    boolean existsByQuiz(Quiz quiz);
    Result findByUserAndQuiz(User user , Quiz quiz);
}
