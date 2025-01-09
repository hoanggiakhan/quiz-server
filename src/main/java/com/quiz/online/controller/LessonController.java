package com.quiz.online.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.online.dto.request.LessonRequest;
import com.quiz.online.entity.Lesson;
import com.quiz.online.repository.LessonRepository;
import com.quiz.online.service.LessonService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
public class LessonController {
    LessonService lessonService;
    @PostMapping("/create")
    public ResponseEntity<?> createLesson(@RequestBody LessonRequest lessonRequest){
        lessonService.createLesson(lessonRequest);
        return ResponseEntity.ok("Lesson created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLessons(@PathVariable String id){
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }
}
