package com.quiz.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.online.entity.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, String> {

}
