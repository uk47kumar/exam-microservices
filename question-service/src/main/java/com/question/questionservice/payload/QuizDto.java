package com.question.questionservice.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizDto {
    private Long id;
    private String title;
    private Integer maxMarks;
    private Integer noOfQuestions;
}
