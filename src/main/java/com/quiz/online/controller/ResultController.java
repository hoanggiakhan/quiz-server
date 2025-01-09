package com.quiz.online.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.online.entity.Result;
import com.quiz.online.service.ResultService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/result")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
public class ResultController {
    ResultService resultService;

    @PostMapping("/{userId}/{quizId}")
    public void saveResult(@RequestBody Result result , @PathVariable String userId , @PathVariable String quizId){
        resultService.saveResult(userId, quizId, result);
    }
}
