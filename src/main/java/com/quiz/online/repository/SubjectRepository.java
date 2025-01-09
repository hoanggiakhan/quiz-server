package com.quiz.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.quiz.online.entity.Subject;
@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
    Subject findByName(String name);
}
