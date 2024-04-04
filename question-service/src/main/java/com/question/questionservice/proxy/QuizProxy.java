package com.question.questionservice.proxy;

import com.question.questionservice.payload.QuizDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "QUIZ-SERVICE")
public interface QuizProxy {

    @GetMapping("/api/quizzes/{quizId}")
    QuizDto getQuiz(@PathVariable("quizId") Long quizId);
}
