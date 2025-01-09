package com.quiz.online.service.post;

import java.util.List;

import org.springframework.stereotype.Service;

import com.quiz.online.dto.PostDTO;
import com.quiz.online.dto.request.PostRequest;
import com.quiz.online.entity.Post;
import com.quiz.online.entity.User;
import com.quiz.online.repository.PostRepository;
import com.quiz.online.repository.UserRepository;
import com.quiz.online.service.comment.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PostService {
    PostRepository postRepository;
    UserRepository userRepository;
    CommentService commentService;
    public PostDTO savePost(PostRequest request , String userID){
        User user = userRepository.findById(userID).orElse(null);
        Post post = new Post();
        post.setContent(request.getContent());
        post.setImageUrl(request.getImageUrl());
        post.setUser(user);
        postRepository.save(post);
        return new PostDTO(post.getId(), post.getContent(), post.getCreatedAt(), post.getUser().getFullName(), post.getImageUrl() , null , user.getAvatarUrl());
    }

    public List<PostDTO> getAllPost() {
    
        List<Post> posts = postRepository.findAll();
    
        return posts.stream()
            .map(post -> new PostDTO(
                post.getId(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUser () != null ? post.getUser ().getFullName() : "Unknown User", // Kiểm tra null
                post.getImageUrl(),
                commentService.getCommentByPost(post.getId()),
                post.getUser().getAvatarUrl()
            ))
            .toList();
    }
    
}
