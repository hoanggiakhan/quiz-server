package com.quiz.online.service.comment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.quiz.online.dto.CommentDTO;
import com.quiz.online.entity.Comment;
import com.quiz.online.entity.Post;
import com.quiz.online.entity.User;
import com.quiz.online.repository.CommentRepository;
import com.quiz.online.repository.PostRepository;
import com.quiz.online.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CommentService {
    CommentRepository commentRepository;
    UserRepository userRepository;
    PostRepository postRepository;

    public CommentDTO createComment(Comment comment, String userId, String postId) {
        User user = userRepository.findById(userId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);

        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);
        return new CommentDTO(comment.getId(), comment.getContent(), comment.getCreatedAt(), user.getFullName(), user.getAvatarUrl());
    }

    public List<CommentDTO> getCommentByPost(String postId) {
        Post post = postRepository.findById(postId).orElse(null);
        List<Comment> comments = commentRepository.findByPost(post);
        return comments.stream().map(comment -> new CommentDTO(comment.getId(), comment.getContent(),
                comment.getCreatedAt(), comment.getUser().getFullName(),comment.getUser().getAvatarUrl())).toList();
    }
}
