package com.quiz.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.online.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,String>{
    
}
