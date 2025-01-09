package com.quiz.online.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Value("${cloud_name}")
    private String cloudName;

    @Value("${cloud_APIKey}")
    private String apiKey;

    @Value("${cloud_Secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", cloudName, 
            "api_key", apiKey, 
            "api_secret", apiSecret));
    }
}