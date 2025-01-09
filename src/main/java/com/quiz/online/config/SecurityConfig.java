package com.quiz.online.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.quiz.online.dto.Enpoint;
import com.quiz.online.service.jwt.JwtFilter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfig {

    JwtFilter filter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request -> request
                .requestMatchers(HttpMethod.GET, Enpoint.publicGet).permitAll()
                .requestMatchers(HttpMethod.POST, Enpoint.publicPost).permitAll()
                .requestMatchers(HttpMethod.PUT, Enpoint.publicPut).permitAll()
                .requestMatchers(HttpMethod.DELETE, Enpoint.publicDelete).permitAll()
                .requestMatchers(HttpMethod.PATCH , Enpoint.publicPatch).permitAll()
                .requestMatchers(HttpMethod.GET, Enpoint.adminGet).hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, Enpoint.adminPost).hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, Enpoint.adminPut).hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, Enpoint.adminDelete).hasAuthority("ADMIN")
                .anyRequest().authenticated())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
             httpSecurity.cors(cors -> {
            cors.configurationSource(request -> {
                CorsConfiguration corsConfig = new CorsConfiguration();
                corsConfig.addAllowedOrigin("*");
                corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE" , "PATCH"));
                corsConfig.addAllowedHeader("*");
                return corsConfig;
            });
        });
        httpSecurity.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}