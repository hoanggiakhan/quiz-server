package com.quiz.online.service.user;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.online.dto.request.ChangePassword;
import com.quiz.online.dto.request.Email;
import com.quiz.online.dto.request.EmailRequest;
import com.quiz.online.dto.response.UserRank;
import com.quiz.online.dto.response.UserResponse;
import com.quiz.online.entity.Result;
import com.quiz.online.entity.User;
import com.quiz.online.repository.ResultRepository;
import com.quiz.online.repository.UserRepository;
import com.quiz.online.service.kafka.EmailProducer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  UserRepository userRepository;
  PasswordEncoder passwordEncoder;
  ResultRepository resultRepository;
  EmailProducer emailProducer;

  public UserResponse createUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole("USER");
    if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
      throw new RuntimeException("Username hoặc email đã tồn tại");
    }
    userRepository.save(user);
    // Tạo thông báo email
    EmailRequest emailRequest = new EmailRequest();
    emailRequest.setTo(user.getEmail());
    emailRequest.setSubject("Chào mừng bạn đến với trang web của chúng tôi!");
    emailRequest.setBody("Cảm ơn bạn đã đăng ký tài khoản. Chúc bạn có trải nghiệm tuyệt vời!");
    // Gửi email qua Kafka
    try {
      ObjectMapper mapper = new ObjectMapper();
      String emailMessage = mapper.writeValueAsString(emailRequest);
      emailProducer.sendEmailMessage(emailMessage);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return UserResponse.builder()
        .fullName(user.getFullName())
        .username(user.getUsername())
        .email(user.getEmail())
        .role(user.getRole())
        .build();
  }

  public UserResponse profileUser(String userId) {
    User user = userRepository.findById(userId).orElse(null);
    return new UserResponse(user.getFullName(), user.getUsername(), user.getPassword(), user.getEmail(), user.getRole(),
        user.getAvatarUrl());
  }

  public void changeAvatar(String avatarUrl, String userId) {
    User user = userRepository.findById(userId).orElse(null);
    user.setAvatarUrl(avatarUrl);
    userRepository.saveAndFlush(user);
  }

  public void changePassword(ChangePassword changePassword , String userId) {
    User user = userRepository.findById(userId).orElse(null);
    if (passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
      user.setPassword(changePassword.getNewPassword());
    } else {
      throw new RuntimeException("Mật khẩu cũ không đúng");
    }
    userRepository.saveAndFlush(user);
  }

  public double getTotalScore(String userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    return resultRepository.findByUser(user).stream().mapToDouble(Result::getScore).sum();
  }

  public List<UserRank> getUsersSortedByScore() {
    List<User> users = userRepository.findAll();
    return users.stream()
        .map(user -> {
          double totalScore = getTotalScore(user.getUserId());
          return new UserRank(user.getUserId(), user.getFullName(), totalScore);
        })
        .sorted((u1, u2) -> Double.compare(u2.getScore(), u1.getScore()))
        .collect(Collectors.toList());
  }

  public void forgotPassword(Email email) {
    User user = userRepository.findByEmail(email.getEmail());
    if (user == null) {
      throw new RuntimeException("Email không tồn tại");
    } else {
      // Tạo mật khẩu ngẫu nhiên
      String newPassword = generateRandomPassword(6); // Mật khẩu thô (chưa mã hóa)

      // Mã hóa mật khẩu mới
      String encodedPassword = passwordEncoder.encode(newPassword);

      // Cập nhật mật khẩu vào cơ sở dữ liệu
      user.setPassword(encodedPassword);
      try {
        userRepository.saveAndFlush(user);

        // Tạo thông báo email
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(user.getEmail());
        emailRequest.setSubject("Thay đổi mật khẩu");
        emailRequest.setBody("Mật khẩu mới của bạn là: " + newPassword);

        // Gửi email qua Kafka
        ObjectMapper mapper = new ObjectMapper();
        String emailMessage = mapper.writeValueAsString(emailRequest);
        emailProducer.sendEmailMessage(emailMessage);

      } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Lỗi khi thay đổi mật khẩu hoặc gửi email");
      }
    }
  }

  public static String generateRandomPassword(int length) {
    SecureRandom random = new SecureRandom();
    StringBuilder password = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      int index = random.nextInt(CHARACTERS.length());
      password.append(CHARACTERS.charAt(index));
    }

    return password.toString();
  }
}
