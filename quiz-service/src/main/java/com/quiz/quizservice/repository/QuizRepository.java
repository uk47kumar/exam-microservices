package com.quiz.quizservice.repository;

import com.quiz.quizservice.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
