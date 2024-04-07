package com.question.questionservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String question;
    @Column(length = 1000)
    private String answer;
    @Column(length = 1000)
    private String option1;
    @Column(length = 1000)
    private String option2;
    @Column(length = 1000)
    private String option3;
    @Column(length = 1000)
    private String option4;
    private Long quizId;
}
