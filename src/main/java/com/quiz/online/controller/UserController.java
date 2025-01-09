package com.quiz.online.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.online.dto.request.ChangePassword;
import com.quiz.online.dto.request.Email;
import com.quiz.online.dto.response.UserRank;
import com.quiz.online.dto.response.UserResponse;
import com.quiz.online.entity.User;
import com.quiz.online.service.user.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
public class UserController {
    UserService userService;
    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserResponse> profileUser(@PathVariable String userId){
        return ResponseEntity.ok(userService.profileUser(userId));
    }
     @PatchMapping("/change-avatar/{userId}")
     public void changeAvatar(@RequestBody String avatarUrl , @PathVariable String userId){
        userService.changeAvatar(avatarUrl, userId);
     }
     @PatchMapping("/change-password/{userId}")
     public void changePassword(@RequestBody ChangePassword changePassword , @PathVariable String userId){
        userService.changePassword(changePassword, userId);
     }
     @GetMapping("/rank")
     public List<UserRank> getAllUserRanks(){
        return userService.getUsersSortedByScore();
     }

     @PostMapping("/forgot-password")
     public void forgotPassword(@RequestBody Email email){
        userService.forgotPassword(email);
     }
}
