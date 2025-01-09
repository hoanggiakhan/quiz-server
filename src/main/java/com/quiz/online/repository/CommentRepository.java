package com.quiz.online.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.online.entity.Comment;
import com.quiz.online.entity.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String>{
     List<Comment> findByPost(Post post);
}
