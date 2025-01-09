package com.quiz.online.service.subject;

import java.util.List;

import org.springframework.stereotype.Service;

import com.quiz.online.dto.SubjectDTO;
import com.quiz.online.entity.Subject;
import com.quiz.online.repository.SubjectRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SubjectService {
    SubjectRepository subjectRepository;

    public SubjectDTO createSubject(Subject subject) {
        Subject newSubject = subjectRepository.save(subject);
        return new SubjectDTO(newSubject.getSubjectId(), newSubject.getName(), null);
    }


    public List<SubjectDTO> getAllSubject(){
        List<Subject> subjects = subjectRepository.findAll();

        return subjects.stream()
        .map(subject -> new SubjectDTO(subject.getSubjectId(), subject.getName(), subject.getQuizzes().stream()
        .map(quiz->quiz.getTitle())
        .toList()))
        .toList();
    }
}
