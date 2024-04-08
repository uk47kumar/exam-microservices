package com.quiz.quizservice.service.impl;

import com.quiz.quizservice.entity.Quiz;
import com.quiz.quizservice.exception.ResourceNotFoundException;
import com.quiz.quizservice.payload.QuestionDto;
import com.quiz.quizservice.payload.QuizDto;
import com.quiz.quizservice.proxy.QuestionProxy;
import com.quiz.quizservice.repository.QuizRepository;
import com.quiz.quizservice.service.QuizService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final ModelMapper modelMapper;
    private final QuestionProxy questionProxy;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository,
                           ModelMapper modelMapper,
                           QuestionProxy questionProxy) {
        this.quizRepository = quizRepository;
        this.modelMapper = modelMapper;
        this.questionProxy = questionProxy;
    }

    @Override
    public QuizDto createQuiz(QuizDto quizDto) {
        Quiz quiz = mapToEntity(quizDto);
        Quiz savedQuiz = quizRepository.save(quiz);
        return mapToDto(savedQuiz);
    }

    @Override
    public QuizDto getQuiz(Long id) {

        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("quiz not found with id: " + id));

        List<QuestionDto> allQuestions = questionProxy.getAllQuestionOfQuiz(quiz.getId());

        // shuffle the list of questions randomly
        Collections.shuffle(allQuestions);
        // find the no of Question to show
        int noOfQuestion = quiz.getNoOfQuestions();
        List<QuestionDto> selectedQuestion = allQuestions.subList(0, Math.min(noOfQuestion, allQuestions.size()));

        quiz.setQuestions(selectedQuestion);

        return mapToDto(quiz);
    }

    @Override
    public List<QuizDto> getAllQuiz() {
        List<Quiz> quizList = quizRepository.findAll();

        // finding the question related to that quizId
        List<Quiz> quizWithQuestions = quizList
                .stream()
                .map((quiz -> {
                    quiz.setQuestions(questionProxy.getAllQuestionOfQuiz(quiz.getId()));
                    return quiz;
                }))
                .collect(Collectors.toList());

        // converting to dto for the response
        List<QuizDto> quizDto = quizWithQuestions
                .stream()
                .map((quiz -> mapToDto(quiz)))
                .collect(Collectors.toList());

        return quizDto;
    }

    @Override
    public void deleteQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("quiz not found with id: " + id));
        questionProxy.deleteQuestionsByQuizId(quiz.getId());
        quizRepository.delete(quiz);
    }

    private QuizDto mapToDto(Quiz quiz) {
        return modelMapper.map(quiz, QuizDto.class);
    }

    private Quiz mapToEntity(QuizDto quizDto) {
        return modelMapper.map(quizDto, Quiz.class);
    }

}
