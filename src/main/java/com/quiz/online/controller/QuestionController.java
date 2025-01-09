package com.quiz.online.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.online.dto.QuestionDTO;
import com.quiz.online.service.question.QuestionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
public class QuestionController {
    QuestionService questionService;

    @GetMapping("/{quizId}")
    public ResponseEntity<?> getQuestionsByQuizId(@PathVariable String quizId){
        return ResponseEntity.ok(questionService.getQuestionsByQuizId(quizId));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createQuestion(@RequestBody QuestionDTO questionDTO){
        return ResponseEntity.ok(questionService.createQuestion(questionDTO));
    }
}
