package com.quiz.online.service;

import org.springframework.stereotype.Service;

import com.quiz.online.entity.Quiz;
import com.quiz.online.entity.Result;
import com.quiz.online.entity.User;
import com.quiz.online.repository.QuizRepository;
import com.quiz.online.repository.ResultRepository;
import com.quiz.online.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResultService {
    ResultRepository resultRepository;
    UserRepository userRepository;
    QuizRepository quizRepository;
    public void saveResult(String userId, String quizId, Result result) {
        // Tìm kiếm người dùng và bài kiểm tra
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new RuntimeException("Quiz not found"));
    
        // Kiểm tra kết quả đã tồn tại chưa
        Result existingResult = resultRepository.findByUserAndQuiz(user, quiz);
        if (existingResult != null) {
            // Cập nhật kết quả đã tồn tại
            existingResult.setScore(result.getScore());
            existingResult.setCorrectAnswers(result.getCorrectAnswers());
            existingResult.setIncorrectAnswers(result.getIncorrectAnswers());
            existingResult.setTimeTaken(result.getTimeTaken());
            existingResult.setUserAnswers(result.getUserAnswers());
            resultRepository.saveAndFlush(existingResult);
        } else {
            // Gắn quan hệ và lưu kết quả mới
            result.setQuiz(quiz);
            result.setUser(user);
            resultRepository.save(result);
        }
    }
    
}