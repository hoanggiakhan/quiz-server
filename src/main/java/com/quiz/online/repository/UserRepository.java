package com.quiz.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.online.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {


     public Optional<User> findByUsername(String username);

     public boolean existsByUsername(String username);
     public boolean existsByEmail(String email);
     User findByEmail(String email);
}
