package com.quiz.online.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Subject {
     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
     String subjectId;
     String name;

    @OneToMany(mappedBy = "subject")
    List<Quiz> quizzes;
    @OneToMany(mappedBy = "subject")
    List<Lesson> lessons;
}
