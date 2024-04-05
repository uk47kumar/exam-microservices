package com.question.questionservice.controller;

import com.question.questionservice.payload.QuestionDto;
import com.question.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody QuestionDto questionDto) {
        return new ResponseEntity<>(questionService.createQuestion(questionDto), CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestion(@PathVariable("id") Long id) {
        return ResponseEntity.ok(questionService.getQuestion(id));
    }

    @GetMapping
    public ResponseEntity<List<QuestionDto>> getAllQuestion() {
        return ResponseEntity.ok(questionService.getAllQuestion());
    }

    @GetMapping("/quizzes/{quizId}")
    public ResponseEntity<List<QuestionDto>> getAllQuestionByQuizId(@PathVariable("quizId") Long quizId) {
        return ResponseEntity.ok(questionService.getAllQuestionByQuizId(quizId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteQuestion(id);
        return new ResponseEntity<>("Question deleted successfully", NO_CONTENT);
    }

    @DeleteMapping("/quizzes/{quizId}")
    public ResponseEntity<String> deleteQuestionsByQuizId(@PathVariable("quizId") Long quizId) {
        questionService.deleteQuestionsByQuizId(quizId);
        return new ResponseEntity<>("Multiple Question deleted successfully", NO_CONTENT);
    }

}
