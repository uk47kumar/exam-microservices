package com.quiz.quizservice.proxy;

import com.quiz.quizservice.payload.QuestionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//@FeignClient(url = "http://localhost:8082", value = "Question-Proxy")
@FeignClient(name = "QUESTION-SERVICE")
public interface QuestionProxy {

    @GetMapping("/api/questions/quizzes/{quizId}")
    List<QuestionDto> getAllQuestionOfQuiz(@PathVariable("quizId") Long quizId);

    @DeleteMapping("/api/questions/quizzes/{quizId}")
    void deleteQuestionsByQuizId(@PathVariable("quizId") Long quizId);

}
