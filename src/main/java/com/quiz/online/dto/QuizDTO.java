package com.quiz.online.dto;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

import com.quiz.online.entity.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class QuizDTO {
    String quizId;
    String title;
    String description;
    Time duration;
    boolean isActive = false;
    String nameSubject;
    int grade;
    Status status;
    LocalDateTime createdAt;
}
