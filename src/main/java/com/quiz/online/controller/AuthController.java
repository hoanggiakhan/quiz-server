package com.quiz.online.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.online.dto.request.LoginRequest;
import com.quiz.online.dto.response.JwtResponse;
import com.quiz.online.entity.User;
import com.quiz.online.repository.UserRepository;
import com.quiz.online.service.jwt.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthController {
    UserRepository userRepository;
    JwtService jwtService;
    PasswordEncoder passwordEncoder;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
         User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
         if(user != null){
            if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
                try {
                    String token = jwtService.generateToken(user.getUsername() , user.getRole() , user.getUserId() , user.getAvatarUrl());
                    return ResponseEntity.ok(new JwtResponse(token));
                } catch (AuthenticationException e) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
         }
                 return null;
    }
}
