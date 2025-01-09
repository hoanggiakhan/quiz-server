package com.quiz.online.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.online.dto.QuizDTO;
import com.quiz.online.entity.Result;
import com.quiz.online.service.quiz.QuizService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
public class QuizController {
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizDTO quizDTO){
        return ResponseEntity.ok(quizService.createQuiz(quizDTO));
    }
    
    @GetMapping
    public ResponseEntity<?> getAllQuiz(){
        return ResponseEntity.ok(quizService.getAllQuiz());
    }

    @PostMapping("status/{userId}/{quizId}")
    public void createStatus(@PathVariable String userId , @PathVariable String quizId){
        quizService.createStatusQuiz(userId, quizId);
    }
    @GetMapping("/{userId}")
    public List<QuizDTO> getQuizByUser(@PathVariable String userId){
       return quizService.getQuizByUserId(userId);
    }

    @PatchMapping("/{userId}/{quizId}")
    public void start(@PathVariable String userId , @PathVariable String quizId){
        quizService.start(userId, quizId);
    }

    @PostMapping("/{userId}/{quizId}")
    public void quizSubmit(@PathVariable String userId , @PathVariable String quizId , @RequestBody Result result){
        quizService.quizSubmit(userId, quizId , result);
    }
}
