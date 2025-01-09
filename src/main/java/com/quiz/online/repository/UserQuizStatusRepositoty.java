package com.quiz.online.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.online.entity.Quiz;
import com.quiz.online.entity.User;
import com.quiz.online.entity.UserQuizStatus;

@Repository
public interface UserQuizStatusRepositoty extends JpaRepository<UserQuizStatus,String>{
    UserQuizStatus findByUserAndQuiz(User user , Quiz quiz);
}
