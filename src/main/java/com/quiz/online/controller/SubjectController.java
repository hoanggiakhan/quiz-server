package com.quiz.online.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.quiz.online.dto.SubjectDTO;
import com.quiz.online.entity.Subject;
import com.quiz.online.service.subject.SubjectService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
public class SubjectController {
    SubjectService subjectService;

    @PostMapping("/create")
    public ResponseEntity<SubjectDTO> createSubject(@RequestBody Subject subject){
        return ResponseEntity.ok(subjectService.createSubject(subject));
    }

    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getAllSubject(){
        return ResponseEntity.ok(subjectService.getAllSubject());
    }
}
