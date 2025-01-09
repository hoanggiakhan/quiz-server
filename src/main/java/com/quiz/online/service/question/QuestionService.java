package com.quiz.online.service.question;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.quiz.online.dto.QuestionDTO;
import com.quiz.online.entity.Question;
import com.quiz.online.entity.Quiz;
import com.quiz.online.repository.QuestionRepository;
import com.quiz.online.repository.QuizRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class QuestionService {
    QuizRepository quizRepository;
    QuestionRepository questionRepository;
    public List<QuestionDTO> getQuestionsByQuizId(String quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        List<QuestionDTO> questionDTOs = quiz.getQuestions().stream().map(question -> {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestionId(question.getQuestionId());
            questionDTO.setContent(question.getContent());
            questionDTO.setQuestionType(question.getQuestionType());
            questionDTO.setChoices(question.getChoices());
            questionDTO.setCorrectAnswers(question.getCorrectAnswers());
            questionDTO.setCreatedAt(question.getCreatedAt());
            questionDTO.setNameQuiz(quiz.getTitle());
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOs;
    }

    public QuestionDTO createQuestion(QuestionDTO questionDTO) {
         Question question = new Question();
            question.setContent(questionDTO.getContent());
            question.setQuestionType(questionDTO.getQuestionType());
            question.setChoices(questionDTO.getChoices());
            question.setCorrectAnswers(questionDTO.getCorrectAnswers());
            question.setCreatedAt(questionDTO.getCreatedAt());
            Quiz quiz = quizRepository.findByTitle(questionDTO.getNameQuiz());
            question.setQuiz(quiz);
            questionRepository.save(question);
            return questionDTO;
    }
}
