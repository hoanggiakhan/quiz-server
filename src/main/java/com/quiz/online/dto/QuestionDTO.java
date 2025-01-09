package com.quiz.online.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionDTO {
     String questionId;
    String content;
    private String questionType;
    private List<String> choices;
    private List<String> correctAnswers;
    LocalDateTime createdAt;
    String nameQuiz;
}
