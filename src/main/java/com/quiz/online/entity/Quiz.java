package com.quiz.online.entity;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String quizId;
    String title;
    String description;
    Time duration;
    boolean isActive; // Trạng thái hoạt động của bài kiểm tra
    int grade;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(name = "user_quiz", joinColumns = @JoinColumn(name = "quiz_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    List<User> users;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    List<Question> questions;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    List<Result> results;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    Subject subject;
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    List<UserQuizStatus> quizStatus;
}