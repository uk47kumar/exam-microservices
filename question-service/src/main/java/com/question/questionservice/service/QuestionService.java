package com.question.questionservice.service;

import com.question.questionservice.payload.QuestionDto;

import java.util.List;

public interface QuestionService {
    QuestionDto createQuestion(QuestionDto questionDto);
    QuestionDto getQuestion(Long id);
    List<QuestionDto> getAllQuestion();
    List<QuestionDto> getAllQuestionByQuizId(Long quizId);
    void deleteQuestion(Long id);
    void deleteQuestionsByQuizId(Long quizId);
}
