package com.quiz.online.service;

import org.springframework.stereotype.Service;

import com.quiz.online.dto.request.LessonRequest;
import com.quiz.online.entity.Lesson;
import com.quiz.online.entity.Subject;
import com.quiz.online.repository.LessonRepository;
import com.quiz.online.repository.SubjectRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LessonService {
     LessonRepository lessonRepository;
    SubjectRepository subjectRepository;
     public void createLesson(LessonRequest request){
       Subject subject = subjectRepository.findByName(request.getSubject());
         Lesson lesson = new Lesson();
         lesson.setTitle(request.getTitle());
         lesson.setContent(request.getContent());
         lesson.setSubject(subject);
        lessonRepository.save(lesson);
     }

     public LessonRequest getLessonById(String id){
        Lesson lesson = lessonRepository.findById(id).orElse(null);
        LessonRequest lessonRequest = new LessonRequest();
        lessonRequest.setTitle(lesson.getTitle());
        lessonRequest.setContent(lesson.getContent());
        lessonRequest.setSubject(lesson.getSubject().getName());
        return lessonRequest;
     }
}
