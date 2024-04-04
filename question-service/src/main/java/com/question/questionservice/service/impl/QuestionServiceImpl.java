package com.question.questionservice.service.impl;

import com.question.questionservice.entity.Question;
import com.question.questionservice.exception.ResourceNotFoundException;
import com.question.questionservice.payload.QuestionDto;
import com.question.questionservice.proxy.QuizProxy;
import com.question.questionservice.repository.QuestionRepository;
import com.question.questionservice.service.QuestionService;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;
    private final QuizProxy quizProxy;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository,
                               ModelMapper modelMapper,
                               QuizProxy quizProxy) {
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
        this.quizProxy = quizProxy;
    }

    @Override
    public QuestionDto createQuestion(QuestionDto questionDto) {

        try {
            quizProxy.getQuiz(questionDto.getQuizId());
        } catch (FeignException.NotFound exception) {
            throw new ResourceNotFoundException("Quiz not found with id: " + questionDto.getQuizId());
        }

        Question question = mapToEntity(questionDto);
        Question savedQuestion = questionRepository.save(question);
        return mapToDto(savedQuestion);
    }

    @Override
    public QuestionDto getQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
        return mapToDto(question);
    }

    @Override
    public List<QuestionDto> getAllQuestion() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionDto> questionDto = questions
                .stream()
                .map((question -> mapToDto(question)))
                .collect(Collectors.toList());
        return questionDto;
    }

    @Override
    public List<QuestionDto> getAllQuestionByQuizId(Long quizId) {

        List<Question> questionList = questionRepository.findByQuizId(quizId);

        List<QuestionDto> questionDto = questionList
                .stream()
                .map((question -> mapToDto(question)))
                .collect(Collectors.toList());
        return questionDto;
    }

    @Override
    public void deleteQuestion(Long id) {

    }

    private QuestionDto mapToDto(Question question) {
        return modelMapper.map(question, QuestionDto.class);
    }

    private Question mapToEntity(QuestionDto questionDto) {
        return modelMapper.map(questionDto, Question.class);
    }
}
