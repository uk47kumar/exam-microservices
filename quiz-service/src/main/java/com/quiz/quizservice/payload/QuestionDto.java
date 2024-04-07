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
public class QuestionDto {
    private Long id;
    private String question;
    private String answer;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private Long quizId;
}
