package com.quiz.quizservice.service;

import com.quiz.quizservice.payload.QuizDto;

import java.util.List;

public interface QuizService {

    QuizDto createQuiz(QuizDto quizDto);
    QuizDto getQuiz(Long id);
    List<QuizDto> getAllQuiz();
    void deleteQuiz(Long id);
}
