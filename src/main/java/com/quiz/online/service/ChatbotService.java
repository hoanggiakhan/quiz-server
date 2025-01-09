package com.quiz.online.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ChatbotService {

    private final WebClient webClient;

    public ChatbotService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.gemini.com/v1/chat").build();
    }

    public String getChatbotResponse(String message) {
        return this.webClient.post()
                .uri("/respond")
                .bodyValue(Map.of("message", message))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

