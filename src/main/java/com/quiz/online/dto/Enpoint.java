package com.quiz.online.dto;

public class Enpoint {
    public final static String[] publicGet = {
        "api/**",
        "/subject/**",
        "/quiz/**",
        "/question/**",
        "/api/posts/**",
        "/user/profile/**",
        "/user/**",
        "lesson/**",
    };

    public final static String[] publicPost = {
        "/api/**" ,
        "subject/**",
        "quiz/**",
        "question/**",
        "quiz/{userId}/**",
        "/user/**",
        "/api/chatbot/**",
        "/api/posts/**",
        "/api/images/**",
        "/api/posts/comment/{userId}/{postId}",
        "/result/{userId}/**",
        "/lesson/**",
        "/api/email/**"
    };
    
    public final static String[] publicPut = {
        "api/**"
    };

    public final static String[] publicDelete = {
        "api/**"
    };
    public final static String[] publicPatch = {
        "quiz/{userId}/**",
        "/user/change-avatar/**",
        "/user/change-password/**"
    };
    public final static String[] adminGet = {
        
    };

    public final static String[] adminPost = {
       
    };

    public final static String[] adminPut = {
       
    };

    public final static String[] adminDelete = {
        
    };
}
