package com.quiz.online.service.quiz;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quiz.online.dto.QuizDTO;
import com.quiz.online.entity.Quiz;
import com.quiz.online.entity.Result;
import com.quiz.online.entity.Status;
import com.quiz.online.entity.User;
import com.quiz.online.entity.UserQuizStatus;
import com.quiz.online.repository.QuizRepository;
import com.quiz.online.repository.SubjectRepository;
import com.quiz.online.repository.UserQuizStatusRepositoty;
import com.quiz.online.repository.UserRepository;
import com.quiz.online.service.ResultService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class QuizService {
    QuizRepository quizRepository;
    SubjectRepository subjectRepository;
    UserRepository userRepository;
    UserQuizStatusRepositoty  quizStatusRepository;
    ResultService resultService;
    public QuizDTO createQuiz(QuizDTO quizDTO) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizDTO.getTitle());
        quiz.setDescription(quizDTO.getDescription());
        quiz.setDuration(quizDTO.getDuration());
        quiz.setGrade(quizDTO.getGrade());
        quiz.setSubject(subjectRepository.findByName(quizDTO.getNameSubject()));
        quizRepository.save(quiz);
        return quizDTO;
    }

    public List<QuizDTO> getAllQuiz() {
        List<Quiz> quizzes = quizRepository.findAllByOrderByCreatedAtAsc(); // hoặc Desc
        return quizzes.stream()
            .map(quiz -> {
                QuizDTO quizDTO = new QuizDTO();
                quizDTO.setQuizId(quiz.getQuizId());
                quizDTO.setDescription(quiz.getDescription());
                quizDTO.setDuration(quiz.getDuration());
                quizDTO.setGrade(quiz.getGrade());
                quizDTO.setActive(quiz.isActive());
                quizDTO.setTitle(quiz.getTitle());
                quizDTO.setNameSubject(quiz.getSubject().getName());
                return quizDTO;
            })
            .toList();
    }
    
    
    public void createStatusQuiz(String userId , String quizId){
          User user = userRepository.findById(userId).orElse(null);
          Quiz quiz = quizRepository.findById(quizId).orElseThrow(()->new RuntimeException("Khong tim thay"));
          UserQuizStatus quizStatus = UserQuizStatus.builder()
          .user(user)
          .quiz(quiz)
          .build();
          quizStatusRepository.save(quizStatus);
    }

    public List<QuizDTO> getQuizByUserId(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return Collections.emptyList();
        }
    
        List<Quiz> allQuizzes = getAllQuiz(user); // Gồm các bài kiểm tra chưa thuộc về người dùng
        allQuizzes.addAll(user.getQuizzes());
        return allQuizzes.stream().map(quiz -> {
            UserQuizStatus userQuizStatus = quizStatusRepository.findByUserAndQuiz(user, quiz);
            Status status = (userQuizStatus != null) ? userQuizStatus.getStatus() : Status.NOT_STARTED;
    
            return QuizDTO.builder()
                .quizId(quiz.getQuizId())
                .description(quiz.getDescription())
                .duration(quiz.getDuration())
                .grade(quiz.getGrade())
                .title(quiz.getTitle())
                .nameSubject(quiz.getSubject().getName())
                .status(status)
                .build();
        }).toList();
    }
    

    public List<Quiz> getAllQuiz(User user) {
    Set<String> userQuizIds = user.getQuizzes().stream()
        .map(Quiz::getQuizId)
        .collect(Collectors.toSet());

    return quizRepository.findAll().stream()
        .filter(quiz -> !userQuizIds.contains(quiz.getQuizId()))
        .collect(Collectors.toList());
}
   
    public void start(String userId , String quizId){
       User user = userRepository.findById(userId).orElse(null);
       Quiz quiz = quizRepository.findById(quizId).orElse(null);
       if(!user.getQuizzes().contains(quiz)){
        user.getQuizzes().add(quiz);
       }
       userRepository.saveAndFlush(user);
    }

    public void quizSubmit(String userId, String quizId , Result result) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        resultService.saveResult(userId, quizId, result);
        UserQuizStatus quizStatus = quizStatusRepository.findByUserAndQuiz(user, quiz);
        if (quizStatus == null) {
            quizStatus = new UserQuizStatus();
            quizStatus.setQuiz(quiz);
            quizStatus.setUser(user);
        }
        quizStatus.setStatus(Status.COMPLETED);
        quizStatusRepository.save(quizStatus);
    }
}
