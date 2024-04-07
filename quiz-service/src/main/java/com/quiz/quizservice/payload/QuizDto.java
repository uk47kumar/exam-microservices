package com.quiz.quizservice.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizDto {
    private Long id;
    private String title;
    private Integer maxMarks;
    private Integer noOfQuestions;
    private List<QuestionDto> questions;
}
