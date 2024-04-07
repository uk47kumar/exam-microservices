package com.question.questionservice.service.impl;

import com.question.questionservice.entity.Question;
import com.question.questionservice.exception.BadRequestException;
import com.question.questionservice.exception.ResourceNotFoundException;
import com.question.questionservice.payload.QuestionDto;
import com.question.questionservice.proxy.QuizProxy;
import com.question.questionservice.repository.QuestionRepository;
import com.question.questionservice.service.QuestionService;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

        if (!isValidAnswer(questionDto)) {
            throw new BadRequestException("At least one option must be equal to the answer");
        }

        try {
            quizProxy.getQuiz(questionDto.getQuizId());
        } catch (FeignException.NotFound exception) {
            throw new ResourceNotFoundException("Quiz not found with id: " + questionDto.getQuizId());
        }

        Question question = mapToEntity(questionDto);
        Question savedQuestion = questionRepository.save(question);
        return mapToDto(savedQuestion);
    }

    private boolean isValidAnswer(QuestionDto questionDto) {

        String answer = questionDto.getAnswer();
        String option1 = questionDto.getOption1();
        String option2 = questionDto.getOption2();
        String option3 = questionDto.getOption3();
        String option4 = questionDto.getOption4();

        return !StringUtils.isEmpty(answer) &&
                (answer.equals(option1) || answer.equals(option2) || answer.equals(option3) || answer.equals(option4));
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
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
        questionRepository.delete(question);
    }

    @Override
    public void deleteQuestionsByQuizId(Long quizId) {
        List<Question> questionList = questionRepository.findByQuizId(quizId);
        questionRepository.deleteAll(questionList);
    }

    private QuestionDto mapToDto(Question question) {
        return modelMapper.map(question, QuestionDto.class);
    }

    private Question mapToEntity(QuestionDto questionDto) {
        return modelMapper.map(questionDto, Question.class);
    }
}
