package com.quiz.online.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.online.dto.CommentDTO;
import com.quiz.online.dto.PostDTO;
import com.quiz.online.dto.request.PostRequest;
import com.quiz.online.entity.Comment;
import com.quiz.online.service.comment.CommentService;
import com.quiz.online.service.post.PostService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class PostController {
    PostService postService;
    CommentService commentService;
   @PostMapping("/{userID}")
    public ResponseEntity<PostDTO> savePost(@RequestBody PostRequest request, @PathVariable String userID) {
        PostDTO postDTO = postService.savePost(request, userID);
        return ResponseEntity.ok(postDTO);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPost();
        return ResponseEntity.ok(posts);
    }
    
    @PostMapping("/comment/{userId}/{postId}")
    public CommentDTO createComment(@RequestBody Comment comment , @PathVariable String userId , @PathVariable String postId){
       return commentService.createComment(comment, userId, postId);
    }

    @GetMapping("/{poostId}")
    public List<CommentDTO> getAllCommentByPost(@PathVariable String postId){
         return commentService.getCommentByPost(postId);
    }
}
