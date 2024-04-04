package com.quiz.quizservice.controller;

import com.quiz.quizservice.payload.QuizDto;
import com.quiz.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<QuizDto> createQuiz(@RequestBody QuizDto quizDto) {
        return new ResponseEntity<>(quizService.createQuiz(quizDto), CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDto> getQuiz(@PathVariable("id") Long id) {
        return ResponseEntity.ok(quizService.getQuiz(id));
    }

    @GetMapping
    public ResponseEntity<List<QuizDto>> getAllQuiz() {
        return ResponseEntity.ok(quizService.getAllQuiz());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable("id") Long id) {
        quizService.deleteQuiz(id);
        return new ResponseEntity<>("Quiz deleted successfully", OK);
    }

}
